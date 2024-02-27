package lab3.endpoints

import cats.MonadThrow
import lab3.config.{AppConfig, HttpServer}
import lab3.domain.auth.response.AccessToken
import lab3.services.{JwtService, SecretService}
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint

final class SecretController[F[_]](service: SecretService[F], config: HttpServer)
    extends Controller[F] {

  private val baseUrl: EndpointInput[Unit] = "api" / "v1" / "secret"
  private val tag: String = "Секрет"

  private val security = auth.oauth2.clientCredentialsFlow(
    tokenUrl = s"http://localhost:${config.port}/api/v1/auth/token"
  )

  def secret: Endpoint[Unit, AccessToken, Unit, String, Any] =
    endpoint
      .tag(tag)
      .post
      .in(baseUrl)
      .in(security)
      .mapIn[AccessToken](AccessToken.apply _)(_.id)
      .out(stringBody)

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    secret.serverLogicSuccess(service.getSecret)
  )
}

object SecretController {

  def impl[F[_]: MonadThrow](jwtService: JwtService[F], config: AppConfig): SecretController[F] = {
    val service = SecretService.impl[F](jwtService)

    new SecretController(service, config.http)
  }

}
