package net.aotter.clamlab

import io.vertx.core.DeploymentOptions
import io.vertx.core.logging.SLF4JLogDelegateFactory
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import net.aotter.clamlab.verticle.WebServerVerticle
import org.koin.core.context.startKoin

class MainVerticle : CoroutineVerticle() {


  override suspend fun start() {
    // set vertx logger delegate factory to slf4j
    val logFactory = System.getProperty("org.vertx.logger-delegate-factory-class-name")
    if (logFactory == null) {
      System.setProperty(
        "org.vertx.logger-delegate-factory-class-name",
        SLF4JLogDelegateFactory::class.java.name
      )
    }

    startKoin {
      modules(module)
    }

    vertx.deployVerticle(WebServerVerticle::class.java, DeploymentOptions()).await()
  }


}

