package eu.fakod

import io.vertx.core.{AsyncResult, Handler}
import java.{lang => jl}


object VertxImplicits {

  type jLong = jl.Long

  implicit def toHandler[T, That](f: T => That): Handler[T] =
    new Handler[T] {
      override def handle(event: T): Unit = f(event)
    }

  implicit class ToAsyncResultWrapper[T](ar: AsyncResult[T]) {
    def wrap: AsyncResultWrapper[T] = if (ar.succeeded) Success(ar.result) else Failure(ar.cause())

    def onComplete[That](f: PartialFunction[AsyncResultWrapper[T], That]): Unit = f(wrap)
  }

}

/**
  * AsyncResultWrapper wraps a vertx AsyncResult
  *
  * @tparam T Wrapped Type
  */
sealed abstract class AsyncResultWrapper[+T]

final case class Failure[+T](exception: Throwable) extends AsyncResultWrapper[T]

final case class Success[+T](value: T) extends AsyncResultWrapper[T]
