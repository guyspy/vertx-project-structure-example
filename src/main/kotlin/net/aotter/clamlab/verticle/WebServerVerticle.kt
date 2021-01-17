package net.aotter.clamlab.verticle

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import net.aotter.clamlab.extension.getLogger
import net.aotter.clamlab.repository.mongo.PostRepository
import net.aotter.clamlab.router.api.apiRouter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class WebServerVerticle : CoroutineVerticle(), KoinComponent {

  private val logger = getLogger()

  val postRepository: PostRepository by inject()

  override suspend fun start() {
    with(vertx) {

      val router = Router.router(this)

      router.route()
        .handler(BodyHandler.create().setUploadsDirectory("media").setDeleteUploadedFilesOnEnd(true))
        .handler(LoggerHandler.create())

      router.mountSubRouter("/api", apiRouter())

      postRepository

      createHttpServer()
        .requestHandler(router)
        .listen(8888)
        .await()

      logger.info("HTTP server started on port 8888")

    }
  }
}

