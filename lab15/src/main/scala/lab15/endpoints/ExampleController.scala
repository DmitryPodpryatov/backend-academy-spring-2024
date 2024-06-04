package lab15.endpoints

import cats.Applicative
import cats.syntax.functor._
import lab15.models.User
import lab15.services.PostgresClientService
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

import java.util.UUID

class ExampleController[F[_]: Applicative](database: PostgresClientService[F])
    extends Controller[F] {

  def insert: Full[Unit, Unit, User, Unit, Unit, Any, F] =
    endpoint
      .post
      .tag("db")
      .in("insert")
      .in(jsonBody[User])
      .serverLogicSuccess { user =>
        database.insert(user).void
      }

  def select: Full[Unit, Unit, UUID, Unit, Option[User], Any, F] =
    endpoint
      .get
      .tag("db")
      .in("select")
      .in(query[UUID]("id"))
      .out(jsonBody[Option[User]])
      .serverLogicSuccess { id =>
        database.select(id)
      }

  def update: Full[Unit, Unit, User, Unit, Option[User], Any, F] =
    endpoint
      .post
      .tag("db")
      .in("update")
      .in(jsonBody[User])
      .out(jsonBody[Option[User]])
      .serverLogicSuccess { user =>
        database.update(user)
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    insert,
    select,
    update
  )
}

object ExampleController {

  def apply[F[_]: Applicative](database: PostgresClientService[F]): ExampleController[F] = {
    new ExampleController[F](database)
  }

}
