package lab6.services

import cats.Monad
import cats.effect.Async
import cats.effect.std.Dispatcher
import cats.syntax.functor._
import sttp.client3._
import sttp.client3.httpclient.cats.HttpClientCatsBackend

trait HttpClientService[F[_]] {
  def google: F[String]
}

object HttpClientService {

  final private class Impl[F[_]: Monad](client: SttpBackend[F, Any]) extends HttpClientService[F] {
    override def google: F[String] = {
      val request = basicRequest.get(uri"https://google.com").response(asStringAlways)

      client.send(request).map(_.body)
    }
  }

  def impl[F[_]: Async](implicit dispatcher: Dispatcher[F]): F[HttpClientService[F]] = {
    for {
      backend <- HttpClientCatsBackend[F](dispatcher)
    } yield new Impl[F](backend)
  }

}
