package net.aotter.clamlab.router.api

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import net.aotter.clamlab.extension.coroutineRespond
import net.aotter.clamlab.extension.sendException
import java.lang.RuntimeException

suspend fun Vertx.apiRouter(): Router {

  val router = Router.router(this)

  router.route("/*").failureHandler { it.sendException() }
  router.route("/foo").coroutineRespond { JsonObject().put("foo", "bar") }
  router.route("/exception").coroutineRespond { throw RuntimeException("GG") }

  return router
}
