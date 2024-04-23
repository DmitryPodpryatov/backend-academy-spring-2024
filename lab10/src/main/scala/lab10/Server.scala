package lab10

import cats.effect.{Async, IO}
import com.comcast.ip4s._
import lab10.config.AppConfig
import lab10.database.{FlywayMigration, makeTransactor}
import lab10.endpoints.ExampleController
import lab10.services.ConsumerService._
import lab10.services.{PostgresClientService, S3ClientService}
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
      transactor <- makeTransactor[IO](config.database)
      consumer <- services.ConsumerService[IO](config.kafka)
      _ <- Async[IO].background(consumer.run(config.kafka))
    } yield transactor).use { transactor =>
      for {
        _ <- FlywayMigration.clear[IO](config.database)
        _ <- FlywayMigration.migrate[IO](config.database)

        postgresClient = PostgresClientService[IO](transactor)
        s3Client = S3ClientService[IO]()
        controller = ExampleController[IO]()

        endpoints = List(
          controller.endpoints
        ).flatten

        swagger = SwaggerInterpreter(
          swaggerUIOptions = SwaggerUIOptions(
            pathPrefix = List("swagger"),
            yamlName = "swagger.yaml",
            contextPath = List(),
            useRelativePaths = false,
            showExtensions = false
          )
        ).fromServerEndpoints[IO](endpoints, "Lab 8", "1.0.0")

        httpApp = Http4sServerInterpreter[IO]()
          .toRoutes(swagger ++ endpoints)
          .orNotFound

        port <-
          IO.fromOption(Port.fromInt(config.http.port))(new RuntimeException("Invalid http port"))

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
