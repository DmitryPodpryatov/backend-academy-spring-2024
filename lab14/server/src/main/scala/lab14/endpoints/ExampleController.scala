package lab14.endpoints

import cats.Applicative
import lab14.config.AppConfig
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

class ExampleController[F[_]: Applicative](config: AppConfig)
    extends Controller[F] {

  def version: Full[Unit, Unit, Unit, Unit, String, Any, F] =
    endpoint
      .get
      .in("version")
      .out(stringBody)
      .serverLogicSuccess { _ =>
        Applicative[F].pure("hello there")
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    version
  )
}

object ExampleController {

  def apply[F[_]: Applicative](config: AppConfig): ExampleController[F] = {
    new ExampleController[F](config)
  }

}
