package lab7.services

import cats.Monad
import cats.effect.Async
import cats.effect.std.Dispatcher
import cats.syntax.functor._
import sttp.client3._
import sttp.client3.httpclient.cats.HttpClientCatsBackend

import scala.concurrent.duration._

trait HttpClientService[F[_]] {
  def request: F[String]
}

object HttpClientService {

  final private class Impl[F[_]: Monad](client: SttpBackend[F, Any]) extends HttpClientService[F] {
    override def request: F[String] = {
      val request = basicRequest
        .get(uri"https://localhost:1080/request")
        .readTimeout(3.seconds)
        .response(asString)

      client.send(request).map(_.body.fold(identity, identity))
    }
  }

  def impl[F[_]: Async](implicit dispatcher: Dispatcher[F]): F[HttpClientService[F]] = {
    for {
      backend <- HttpClientCatsBackend[F](dispatcher)
    } yield new Impl[F](backend)
  }

}
