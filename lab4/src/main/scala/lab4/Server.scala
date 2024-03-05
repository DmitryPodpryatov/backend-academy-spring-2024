package lab4

import cats.effect.{IO, Sync}
import com.comcast.ip4s._
import doobie.Transactor
import lab4.config.AppConfig
import lab4.database.FlywayMigration
import lab4.endpoints.{ErrorController, PostgresClientController}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger
import pureconfig.ConfigSource
import pureconfig.generic.auto.exportReader
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUIOptions
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object Server {

  def run: IO[Unit] = {
    val config = ConfigSource.default.loadOrThrow[AppConfig]

    database.makeTransactor[IO](config.database).use { xa: Transactor[IO] =>
      for {
        // _ <- FlywayMigration.clear[IO](config.database)
        _ <- FlywayMigration.migrate[IO](config.database)

        endpoints = List(
          ErrorController.impl[IO].endpoints,
          PostgresClientController.impl[IO](xa).endpoints
        ).flatten

        swagger = SwaggerInterpreter(
          swaggerUIOptions = SwaggerUIOptions(
            pathPrefix = List("swagger"),
            yamlName = "swagger.yaml",
            contextPath = List(),
            useRelativePaths = false,
            showExtensions = false
          )
        ).fromServerEndpoints[IO](endpoints, "Lab 4", "1.0.0")

        httpApp = Http4sServerInterpreter[IO]().toRoutes(swagger ++ endpoints).orNotFound
        httpAppWithLogging = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

        port <- Sync[IO].fromOption(
          Port.fromInt(config.http.port),
          new RuntimeException("Invalid http port")
        )
        _ <- IO.println(s"Go to http://localhost:${config.http.port}/swagger to open SwaggerUI")

        _ <- EmberServerBuilder.default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port)
          .withHttpApp(httpAppWithLogging)
          .build
          .useForever
      } yield ()
    }

  }
}
