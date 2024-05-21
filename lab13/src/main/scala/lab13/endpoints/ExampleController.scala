package lab13.endpoints

import cats.effect.MonadCancelThrow
import cats.syntax.functor._
import lab13.models.User
import lab13.services.{PostgresClientService, RedisClientService}
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

import java.util.UUID

class ExampleController[F[_]: MonadCancelThrow](
    database: PostgresClientService[F],
    cache: RedisClientService[F]
) extends Controller[F] {

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

  /* cache */

  def cacheAside: Full[Unit, Unit, UUID, Unit, Option[User], Any, F] =
    endpoint
      .get
      .tag("cached")
      .in("cache–aside")
      .in(query[UUID]("id"))
      .out(jsonBody[Option[User]])
      .serverLogicSuccess { id =>
        ???
      }

  def writeThrough: Full[Unit, Unit, User, Unit, Unit, Any, F] =
    endpoint
      .post
      .tag("cached")
      .in("write-through")
      .in(jsonBody[User])
      .serverLogicSuccess { user =>
        ???
      }

  def fallback: Full[Unit, Unit, UUID, Unit, Option[User], Any, F] =
    endpoint
      .get
      .tag("cached")
      .in("fallback")
      .in(query[UUID]("id"))
      .out(jsonBody[Option[User]])
      .serverLogicSuccess { id =>
        ???
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    insert,
    select,
    update
    // cacheAside,
    // writeThrough,
    // fallback
  )
}

object ExampleController {

  def apply[F[_]: MonadCancelThrow](
      postgresService: PostgresClientService[F],
      redisService: RedisClientService[F]
  ): ExampleController[F] = {
    new ExampleController[F](postgresService, redisService)
  }

}
