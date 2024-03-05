package lab4.services

import cats.effect.Async
import cats.effect.std.Dispatcher
import cats.syntax.applicativeError._
import cats.syntax.either._
import cats.syntax.functor._
import cats.syntax.flatMap._
import lab4.domain.http.{ClientError, ClientResponse}
import sttp.capabilities.WebSockets
import sttp.client3._
import sttp.client3.circe._
import sttp.client3.httpclient.cats.HttpClientCatsBackend

trait HttpClientService[F[_]] {
  def update(in: Int): F[Either[ClientError, ClientResponse]]
}

object HttpClientService {

  final private class Impl[F[_]: Async](backend: SttpBackend[F, WebSockets])
      extends HttpClientService[F] {
    override def update(in: Int): F[Either[ClientError, ClientResponse]] = {
      val request =
        basicRequest
          .post(uri"http://localhost:1080/api/v1/update?in=$in")
          .response(asJsonEither[ClientError, ClientResponse])

      backend.send(request)
        .recoverWith {
          // recover from SttpClientException
          ???
        }
        .map {
          // handle ResponseException and ClientResponse
          ???
        }
    }
  }

  def impl[F[_]: Async](implicit dispatcher: Dispatcher[F]): F[HttpClientService[F]] = {
    for {
      backend <- HttpClientCatsBackend(dispatcher)
    } yield new Impl[F](backend)
  }

}
