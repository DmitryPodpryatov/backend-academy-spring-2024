package lab11.endpoints

import cats.effect.MonadCancelThrow
import cats.syntax.functor._
import doobie.util.transactor.Transactor
import lab11.models.Event
import lab11.services.PostgresClientService
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

import java.util.UUID

class ExampleController[F[_]: MonadCancelThrow](service: PostgresClientService[F])
    extends Controller[F] {

  def insert: Full[Unit, Unit, Event, Unit, Unit, Any, F] =
    endpoint
      .post
      .tag("postgres")
      .in("insert")
      .in(jsonBody[Event])
      .serverLogicSuccess { event =>
        service.insert(event).void
      }

  def select: Full[Unit, Unit, UUID, Unit, Option[Event], Any, F] =
    endpoint
      .get
      .tag("postgres")
      .in("select")
      .in(query[UUID]("id"))
      .out(jsonBody[Option[Event]])
      .serverLogicSuccess { id =>
        service.select(id)
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    insert,
    select
  )
}

object ExampleController {

  def apply[F[_]: MonadCancelThrow](xa: Transactor[F]): ExampleController[F] = {
    val service = PostgresClientService[F](xa)

    new ExampleController[F](service)
  }

}
