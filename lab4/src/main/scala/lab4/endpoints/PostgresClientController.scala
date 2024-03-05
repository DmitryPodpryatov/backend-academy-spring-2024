package lab4.endpoints

import cats.data.EitherT
import cats.effect.MonadCancelThrow
import cats.syntax.applicativeError._
import cats.syntax.functor._
import doobie.util.transactor.Transactor
import lab4.domain.postgres.PostgresError
import lab4.services.PostgresClientService
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

class PostgresClientController[F[_]: MonadCancelThrow](service: PostgresClientService[F])
    extends Controller[F] {
  private val baseUrl: EndpointInput[Unit] = "api" / "v1"

  def insert: Full[Unit, Unit, String, PostgresError, String, Any, F] =
    endpoint
      .post
      .tag("postgres")
      .in(baseUrl / "insert")
      .in(query[String]("id"))
      .out(stringBody)
      .errorOut(statusCode(StatusCode.InternalServerError).and(jsonBody[PostgresError]))
      .serverLogic { id =>
        EitherT(service.insert(id))
          .map(_.toString)
          .value
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    insert
  )
}

object PostgresClientController {

  def impl[F[_]: MonadCancelThrow](xa: Transactor[F]): PostgresClientController[F] = {
    val service = PostgresClientService.impl[F](xa)

    new PostgresClientController[F](service)
  }

}
