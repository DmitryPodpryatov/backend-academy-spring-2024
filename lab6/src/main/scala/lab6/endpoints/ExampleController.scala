package lab6.endpoints

import cats.effect.Sync
import cats.syntax.apply._
import cats.syntax.functor._
import lab6.services.{HttpClientService, PostgresClientService}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

final class ExampleController[F[_]: Sync: Logger](
    service: HttpClientService[F],
    database: PostgresClientService[F]
) extends Controller[F] {

  def metrics: Full[Unit, Unit, Unit, Unit, String, Any, F] = ???

  def request: Full[Unit, Unit, Unit, Unit, Unit, Any, F] =
    endpoint
      .post
      .in("request")
      .serverLogicSuccess { _ =>
        info"execute query" *>
          service.google.void
      }

  def insert: Full[Unit, Unit, String, Unit, Unit, Any, F] =
    endpoint
      .post
      .in("insert")
      .in(query[String]("id"))
      .serverLogicSuccess { id =>
        info"execute insert" *>
          database.insert(id).void
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    metrics,
    request,
    insert
  )
}

object ExampleController {

  def impl[F[_]: Sync: Logger](
      service: HttpClientService[F],
      database: PostgresClientService[F]
  ): ExampleController[F] = {
    new ExampleController[F](service, database)
  }

}
