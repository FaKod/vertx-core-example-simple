package io.vertx.example.core.http.simple

import io.vertx.core.buffer.Buffer
import io.vertx.core.{Handler, AbstractVerticle}
import io.vertx.core.http.{HttpServerRequest, HttpClientResponse}
import io.vertx.example.util.Runner

object VertxImplicits {

  implicit def toHandler[T, that](f: T => that): Handler[T] =
    new Handler[T] {
      override def handle(event: T): Unit = f(event)
    }

}




object Client extends App {
  Runner.runExample(classOf[Client])
}

class Client extends AbstractVerticle {

  import VertxImplicits._

  override def start() {
    vertx.createHttpClient.getNow(8080, "localhost", "/", { resp: HttpClientResponse =>
      println("Got response " + resp.statusCode)
      resp.bodyHandler({ body: Buffer =>
        println("Got data " + body.toString("ISO-8859-1"))
      })
    }
    )
  }
}




object Server extends App {
  Runner.runExample(classOf[Server])
}

class Server extends AbstractVerticle {

  import VertxImplicits._

  override def start() {
    vertx.createHttpServer.requestHandler({ req: HttpServerRequest =>
      req.response.putHeader("content-type", "text/html").end("<html><body><h1>Hello from vert.x!</h1></body></html>")
    }).listen(8080)
  }
}