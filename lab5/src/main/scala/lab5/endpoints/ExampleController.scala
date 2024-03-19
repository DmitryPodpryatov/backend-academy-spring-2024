package lab5.endpoints

import cats.{Applicative, MonadThrow}
import cats.syntax.apply._
import cats.syntax.flatMap._
import io.circe.syntax._
import lab5.domain.TopSecret
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

final class ExampleController[F[_]: MonadThrow: Logger] extends Controller[F] {

  private val baseUrl: EndpointInput[Unit] = "api" / "v1"

  def example: Full[Unit, Unit, Unit, Unit, Unit, Any, F] =
    endpoint
      .post
      .in(baseUrl / "example")
      .serverLogicSuccess { _ =>
        info"this is some info" *>
          Applicative[F].unit
            .flatTap(_ => warn"this is a warning")
      }

  def masking: Full[Unit, Unit, Unit, Unit, Unit, Any, F] =
    endpoint
      .post
      .in(baseUrl / "masking")
      .serverLogicSuccess { _ =>
        val secret = TopSecret("top secret client id", "veRyveRystr0ngp@ssw0rd")

        error"this is error with secret=${secret.asJson.noSpaces}" *>
          Applicative[F].unit
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    example,
    masking
  )
}

object ExampleController {

  def impl[F[_]: MonadThrow: Logger]: ExampleController[F] = {
    new ExampleController[F]
  }

}
