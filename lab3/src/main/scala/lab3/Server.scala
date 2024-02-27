package lab3

import cats.effect.{IO, Sync}
import com.comcast.ip4s._
import lab3.config.AppConfig
import lab3.endpoints.{AuthController, SecretController}
import lab3.services.JwtService
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

    val jwtService = JwtService.impl[IO](config.security.jwt)
    val authController = AuthController.impl[IO](jwtService, config)
    val secretController = SecretController.impl[IO](jwtService, config)

    val endpoints = List(
      secretController,
      authController
    ).flatMap(_.endpoints)

    val swagger = SwaggerInterpreter(
      swaggerUIOptions = SwaggerUIOptions(
        pathPrefix = List("swagger"),
        yamlName = "swagger.yaml",
        contextPath = List(),
        useRelativePaths = false,
        showExtensions = false
      )
    ).fromServerEndpoints[IO](endpoints, "Lab-03", "1.0.0")

    val httpApp = Http4sServerInterpreter[IO]().toRoutes(swagger ++ endpoints).orNotFound
    val httpAppWithLogging = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

    for {
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
