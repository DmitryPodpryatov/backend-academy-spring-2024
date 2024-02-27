package lab3.services

import cats.Functor
import cats.syntax.functor._
import lab3.domain.auth.response.AccessToken

import scala.util.Random

trait SecretService[F[_]] {
  def getSecret(bearer: AccessToken): F[String]
}

object SecretService {

  final private class Impl[F[_]: Functor](jwtService: JwtService[F]) extends SecretService[F] {
    override def getSecret(bearer: AccessToken): F[String] = {
      for {
        _ <- jwtService.validateToken(bearer)
      } yield wisdoms(Random.nextInt(wisdoms.length))
    }

    private val wisdoms: Array[String] =
      """
        |Hey now, you're an all star
        |Get your game on, go play
        |Hey now, you're a rock star
        |Get the show on, get paid
        |(And all that glitters is gold)
        |Only shootin' stars break the mold
        |""".stripMargin
        .split('\n')
  }

  def impl[F[_]: Functor](jwtService: JwtService[F]): SecretService[F] = new Impl[F](jwtService)

}
