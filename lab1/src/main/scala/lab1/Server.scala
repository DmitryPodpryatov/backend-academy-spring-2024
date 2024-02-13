package lab1

import cats.effect.Async
import com.comcast.ip4s._
import fs2.io.net.Network
import lab1.endpoints.PaymentsController
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger
import sttp.tapir.server.http4s.Http4sServerInterpreter

object Server {

  def run[F[_]: Async: Network]: F[Nothing] = {
    val paymentsController = PaymentsController[F]

    val routes = List(
      paymentsController.transferByPhone
    )

    val httpApp = Http4sServerInterpreter[F]().toRoutes(routes).orNotFound

    val httpAppWithLogging = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

    EmberServerBuilder.default[F]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpAppWithLogging)
      .build
  }.useForever
}
