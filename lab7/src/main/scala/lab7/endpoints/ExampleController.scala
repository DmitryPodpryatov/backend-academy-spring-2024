package lab7.endpoints

import cats.effect.Sync
import lab7.services.HttpClientService
import org.typelevel.log4cats.Logger
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

final class ExampleController[F[_]: Sync: Logger](
    httpClientService: HttpClientService[F]
) extends Controller[F] {

  def request: Full[Unit, Unit, Unit, Unit, String, Any, F] =
    endpoint
      .post
      .in("request")
      .out(stringBody)
      .serverLogicSuccess { _ =>
        httpClientService.request
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    request
  )
}

object ExampleController {

  def impl[F[_]: Sync: Logger](
      httpClientService: HttpClientService[F]
  ): ExampleController[F] = {
    new ExampleController[F](httpClientService)
  }

}
