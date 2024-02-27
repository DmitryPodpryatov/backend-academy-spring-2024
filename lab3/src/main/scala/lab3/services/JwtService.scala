package lab3.services

import cats.MonadThrow
import cats.data.EitherT
import cats.effect.Clock
import cats.syntax.applicative._
import cats.syntax.applicativeError._
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.circe.{Decoder, parser}
import io.circe.syntax._
import lab3.config.security.JwtConfig
import lab3.domain.auth.errors.AuthError
import lab3.domain.auth.response.AccessToken
import lab3.domain.auth.{JsonWebToken, JwtHeader, JwtPayload, JwtSignature}
import org.http4s.client.oauth1.HmacSha256

import java.util.Base64

trait JwtService[F[_]] {
  def generateToken: F[AccessToken]
  def validateToken(accessToken: AccessToken): F[Unit]

  def buildJwt: F[JsonWebToken]
  def decomposeJwt(bearer: AccessToken): F[(String, String, String)]

  def validateSignature(actual: String, expected: String): F[Unit]
  def validateDuration(jwtPayload: JwtPayload): F[Unit]

  def encodeBase64(s: String): String
  def decodeBase64(s: String): String
}

object JwtService {

  final private class Impl[F[_]: MonadThrow: Clock](config: JwtConfig) extends JwtService[F] {
    override def generateToken: F[AccessToken] = {
      for {
        jwt <- buildJwt

        headerBase64 = encodeBase64(jwt.header.asJson.noSpaces)
        payloadBase64 = encodeBase64(jwt.payload.asJson.noSpaces)

        signature <- sign(headerBase64, payloadBase64)
      } yield AccessToken(
        List(headerBase64, payloadBase64, signature).mkString(".")
      )
    }

    override def validateToken(accessToken: AccessToken): F[Unit] = {
      for {
        (jwtHeaderBase64, jwtPayloadBase64, jwtSignature) <- decomposeJwt(accessToken)
        signature <- sign(jwtHeaderBase64, jwtPayloadBase64)

        _ <- validateSignature(jwtSignature, signature)

        jwtPayload <- decodeJson[JwtPayload](decodeBase64(jwtPayloadBase64))
        _ <- validateDuration(jwtPayload)
      } yield ()
    }

    override def buildJwt: F[JsonWebToken] = {
      val jwtHeader = ???
      val jwtPayload = ???
      val jwtSignature = ???

      JsonWebToken(jwtHeader, jwtPayload, jwtSignature).pure[F]
    }

    override def decomposeJwt(bearer: AccessToken): F[(String, String, String)] = {
      val (headerBase64, payloadBase64, signature): (String, String, String) = ???

      (headerBase64, payloadBase64, signature).pure[F]
    }

    override def validateSignature(actual: String, expected: String): F[Unit] = {
      ???
    }

    override def validateDuration(jwtPayload: JwtPayload): F[Unit] = {
      ???
    }

    override def encodeBase64(s: String): String = {
      ???
    }

    override def decodeBase64(s: String): String = {
      ???
    }

    // internal

    private def sign(headerBase64: String, payloadBase64: String): F[String] = {
      ???
    }

    private def decodeJson[A: Decoder](json: String): F[A] = {
      EitherT.fromEither(parser.decode[A](json))
        .getOrElseF(AuthError.InternalError().raiseError[F, A])
    }
  }

  def impl[F[_]: MonadThrow: Clock](config: JwtConfig): JwtService[F] =
    new Impl[F](config)

}
