package net.aotter.clamlab.extension

import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.impl.HttpStatusException
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun Any.getLogger(): Logger = LoggerFactory.getLogger(javaClass)

fun RoutingContext.sendException() {
  this.response().statusCode = this.statusCode()
  this.json(JsonObject().put("message", this.failure().message))
}

/**
 * coroutine wrapper adapted from [io.vertx.ext.web.Route.respond]
 * @param fn
 */
suspend fun <T> Route.coroutineRespond(
  fn: suspend (RoutingContext) -> T
) {
  handler { ctx ->
    CoroutineScope(ctx.vertx().dispatcher()).launch {
      try {
        val body = fn.invoke(ctx)
        if (!ctx.response().headWritten()) {
          if (body == null) {
            ctx.response().setStatusCode(204).end()
          } else {
            val hasContentType = ctx.response().headers().contains(HttpHeaders.CONTENT_TYPE)
            when (body) {
              is Buffer -> {
                if (!hasContentType) {
                  ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                }
                ctx.end(body as Buffer)
              }
              is String -> {
                if (!hasContentType) {
                  ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/html")
                }
                ctx.end(body as String)
              }
              else -> {
                ctx.json(body)
              }
            }
          }
        } else if (body == null) {
          if (!ctx.response().ended()) {
            ctx.end()
          }
        } else {
          ctx.fail(HttpStatusException(500, "Response already written"))
        }
      } catch (e: Exception) {
        getLogger().error("${ctx.request().method()} ${ctx.request().path()}", e)
        ctx.fail(e)
      }
    }
  }
}
