package lab13

import cats.effect.{IO, Sync}
import com.comcast.ip4s._
import lab13.config.AppConfig
import lab13.database.{FlywayMigration, makePostgresTransactor, makeRedisClient}
import lab13.endpoints.ExampleController
import lab13.services.{PostgresClientService, RedisClientService}
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.slf4j._
import org.typelevel.log4cats.syntax._
import org.typelevel.log4cats.{Logger, LoggerFactory}
import pureconfig.ConfigSource
import pureconfig.generic.auto.exportReader
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUIOptions
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object Server {

  def run: IO[Unit] = {
    val config = ConfigSource.default.loadOrThrow[AppConfig]

    implicit val logger: Logger[IO] = LoggerFactory[IO].getLogger

    (for {
      transactor <- makePostgresTransactor[IO](config.postgres)
      redisClient <- makeRedisClient[IO](config.redis)
    } yield (transactor, redisClient)).use { case (transactor, redisClient) =>
      for {
        _ <- FlywayMigration.clear[IO](config.postgres)
        _ <- FlywayMigration.migrate[IO](config.postgres)

        postgresService = PostgresClientService[IO](transactor)
        redisService = RedisClientService[IO](redisClient)

        endpoints = List(
          ExampleController[IO](postgresService, redisService).endpoints
        ).flatten

        swagger = SwaggerInterpreter(
          swaggerUIOptions = SwaggerUIOptions(
            pathPrefix = List("swagger"),
            yamlName = "swagger.yaml",
            contextPath = List(),
            useRelativePaths = false,
            showExtensions = false
          )
        ).fromServerEndpoints[IO](endpoints, "Lab 13", "1.0.0")

        httpApp = Http4sServerInterpreter[IO]()
          .toRoutes(swagger ++ endpoints)
          .orNotFound

        port <- Sync[IO].fromOption(
          Port.fromInt(config.http.port),
          new RuntimeException("Invalid http port")
        )
        _ <- debug"Go to http://localhost:${config.http.port}/swagger to open SwaggerUI"

        _ <- EmberServerBuilder.default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port)
          .withHttpApp(httpApp)
          .build
          .useForever

      } yield ()
    }
  }

}
