package lab3.endpoints

import cats.MonadThrow
import cats.syntax.applicativeError._
import lab3.config.AppConfig
import lab3.domain.auth.errors.AuthError
import lab3.domain.auth.request.AuthTokenRequest
import lab3.domain.auth.response.AuthTokenResponse
import lab3.services.{AuthService, JwtService}
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint

final class AuthController[F[_]: MonadThrow](service: AuthService[F])
    extends Controller[F] {

  private val baseUrl: EndpointInput[Unit] = "api" / "v1" / "auth" / "token"
  private val tag: String = "Авторизация"

  def authToken: Endpoint[Unit, AuthTokenRequest, AuthError, AuthTokenResponse, Any] =
    endpoint
      .tag(tag)
      .post
      .in(baseUrl)
      .in(formBody[AuthTokenRequest])
      .errorOut(jsonBody[AuthError])
      .errorOut(statusCode(StatusCode.InternalServerError))
      .out(jsonBody[AuthTokenResponse])

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    authToken.serverLogic { r =>
      service.generateAccessToken(r).attemptT
        .leftMap(_.asInstanceOf[AuthError])
        .value
    }
  )
}

object AuthController {

  def impl[F[_]: MonadThrow](
      jwtService: JwtService[F],
      config: AppConfig
  ): AuthController[F] = {
    val service = AuthService.impl[F](jwtService, config.security)

    new AuthController[F](service)
  }

}
