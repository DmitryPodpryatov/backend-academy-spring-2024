package lab8.endpoints

import cats.effect.Sync
import cats.syntax.apply._
import lab8.domain.Application
import lab8.services.ProducerService
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

final class ExampleController[F[_]: Sync: Logger](
    producer: ProducerService[F]
) extends Controller[F] {

  def request: Full[Unit, Unit, Application, Unit, Unit, Any, F] =
    endpoint
      .post
      .in("request")
      .in(jsonBody[Application])
      .serverLogicSuccess { in =>
        info"handle request" *>
          producer.produce(in)
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    request
  )
}

object ExampleController {

  def apply[F[_]: Sync: Logger](
      producer: ProducerService[F]
  ): ExampleController[F] = {
    new ExampleController[F](producer)
  }

}
