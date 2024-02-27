package lab3.services

import cats.syntax.applicativeError._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.{Applicative, MonadThrow}
import lab3.config.SecurityConfig
import lab3.domain.auth.errors.AuthError
import lab3.domain.auth.request.{AuthTokenRequest, ClientId, ClientSecret}
import lab3.domain.auth.response.{AuthTokenResponse, TokenType}

trait AuthService[F[_]] {
  def generateAccessToken(
      request: AuthTokenRequest
  ): F[AuthTokenResponse]

  def validateClient(
      clientId: ClientId,
      clientSecret: ClientSecret
  ): F[Unit]
}

object AuthService {

  final private class Impl[F[_]: MonadThrow](
      jwtService: JwtService[F],
      config: SecurityConfig
  ) extends AuthService[F] {
    override def generateAccessToken(
        request: AuthTokenRequest
    ): F[AuthTokenResponse] = {
      for {
        _ <- validateClient(request.clientId, request.clientSecret)

        token <- jwtService.generateToken
      } yield AuthTokenResponse(
        tokenType = TokenType.Bearer,
        expiresIn = ???,
        accessToken = token
      )
    }

    override def validateClient(
        clientId: ClientId,
        clientSecret: ClientSecret
    ): F[Unit] = {
      AuthError.InternalError().raiseError[F, Unit]
    }
  }

  def impl[F[_]: MonadThrow](
      jwtService: JwtService[F],
      config: SecurityConfig
  ): AuthService[F] =
    new Impl[F](jwtService, config)

}
