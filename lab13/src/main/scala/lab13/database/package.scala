package lab13

import cats.effect.{Async, Resource}
import cats.syntax.option._
import dev.profunktor.redis4cats.RedisCommands
import doobie.hikari.{Config, HikariTransactor}
import lab13.config.{PostgresConfig, RedisConfig}

package object database {

  def makePostgresTransactor[F[_]: Async](
      config: PostgresConfig
  ): Resource[F, HikariTransactor[F]] = {
    val hikariConfig = Config(
      jdbcUrl = config.url,
      username = config.user.some,
      password = config.password.some,
      maximumPoolSize = config.poolSize,
      driverClassName = "org.postgresql.Driver".some
    )

    HikariTransactor.fromConfig[F](hikariConfig)
  }

  type RedisClient[F[_]] = RedisCommands[F, String, String]

  def makeRedisClient[F[_]](config: RedisConfig): Resource[F, RedisClient[F]] = {
    ???
  }

}
