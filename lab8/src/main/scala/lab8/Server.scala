package lab8

import cats.effect.IO
import com.comcast.ip4s._
import lab8.config.AppConfig
import lab8.endpoints.ExampleController
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

    /* Инициализировать consumer, producer */
    val producer = ???

    val controller = ExampleController[IO](producer)

    val endpoints = List(
      controller.endpoints
    ).flatten

    val swagger = SwaggerInterpreter(
      swaggerUIOptions = SwaggerUIOptions(
        pathPrefix = List("swagger"),
        yamlName = "swagger.yaml",
        contextPath = List(),
        useRelativePaths = false,
        showExtensions = false
      )
    ).fromServerEndpoints[IO](endpoints, "Lab 8", "1.0.0")

    val httpApp = Http4sServerInterpreter[IO]()
      .toRoutes(swagger ++ endpoints)
      .orNotFound

    for {
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
