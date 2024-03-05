package lab4.endpoints

import cats.{Applicative, MonadThrow}
import lab4.domain.tapir.{FullError, FullResponse, ManyErrors}
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ServerEndpoint.Full

final class ErrorController[F[_]: MonadThrow] extends Controller[F] {

  private val baseUrl: EndpointInput[Unit] = "api" / "v1"
  private val tag: String = "errors"

  // 204 No Content
  def create: Full[Unit, Unit, Unit, Unit, Unit, Any, F] =
    endpoint.tag(tag)
      .post
      .in(baseUrl / "create")
      .serverLogicSuccess(_ => Applicative[F].unit)

  // 429 Too many requests
  def error: Full[Unit, Unit, Unit, Unit, Unit, Any, F] =
    endpoint.tag(tag)
      .post
      .in(baseUrl / "error")
      .serverLogicError(_ => Applicative[F].unit)

  def manyErrors: Full[Unit, Unit, Int, ManyErrors, Unit, Any, F] =
    endpoint.tag(tag)
      .post
      .in(baseUrl / "error" / "many")
      .in(query[Int]("input"))
      .errorOut(
        oneOf[ManyErrors](???)
      )
      .serverLogicError { input =>
        ???
      }

  import cats.syntax.applicativeError._
  def catchingErrors: Full[Unit, Unit, Unit, String, Unit, Any, F] =
    endpoint.tag(tag)
      .post
      .in(baseUrl / "catch")
      .errorOut(stringBody)
      .serverLogicError { _ =>
        ManyErrors.Forbidden("message").raiseError[F, String]
          // total function
          .handleError {
            case _: ManyErrors.Forbidden => "catched"
            case _                       => "skip"
          }
        // .recover  PartialFunction[E, A] => F[A]
        // .recoverWith  => PartialFunction[E, F[A]] => F[A]
        // .attempt  => F[Either[E, A]]
      }

  def full: Full[Unit, Unit, Int, FullError, FullResponse, Any, F] =
    endpoint.tag(tag)
      .post
      .in(baseUrl / "full")
      .in(query[Int]("in"))
      .out(
        oneOf[FullResponse](
          ???
        )
      )
      .errorOut(
        oneOf[FullError](
          ???
        )
      )
      .serverLogic {
        ???
      }

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    create,
    error,
    manyErrors,
    catchingErrors,
    full
  )
}

object ErrorController {

  def impl[F[_]: MonadThrow]: ErrorController[F] = {
    new ErrorController[F]
  }

}
