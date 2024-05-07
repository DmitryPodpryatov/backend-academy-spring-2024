package lab11

import cats.effect.{IO, Sync}
import com.comcast.ip4s._
import doobie.Transactor
import lab11.config.AppConfig
import lab11.database.FlywayMigration
import lab11.endpoints.ExampleController
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

    database.makeTransactor[IO](config.database).use { xa: Transactor[IO] =>
      for {
        // _ <- FlywayMigration.clear[IO](config.database)
        _ <- FlywayMigration.migrate[IO](config.database)

        endpoints = List(
          ExampleController[IO](xa).endpoints
        ).flatten

        swagger = SwaggerInterpreter(
          swaggerUIOptions = SwaggerUIOptions(
            pathPrefix = List("swagger"),
            yamlName = "swagger.yaml",
            contextPath = List(),
            useRelativePaths = false,
            showExtensions = false
          )
        ).fromServerEndpoints[IO](endpoints, "Lab 11", "1.0.0")

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
