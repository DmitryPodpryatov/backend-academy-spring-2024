package lab2

import cats.effect.{IO, Sync}
import com.comcast.ip4s._
import doobie.Transactor
import lab2.config.AppConfig
import lab2.database.FlywayMigration
import lab2.endpoints.{CarController, InsuranceController}
import lab2.repository.{AggregateRepository, CarRepository}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger
import pureconfig.ConfigSource
import pureconfig.generic.auto.exportReader
import sttp.tapir.server.http4s.Http4sServerInterpreter

object Server {

  def run: IO[Unit] = {

    val config = ConfigSource.default.loadOrThrow[AppConfig]

    database.makeTransactor[IO](config.database).use { xa: Transactor[IO] =>
      val carRepository = CarRepository.impl[IO](xa)
      val aggregateRepository = AggregateRepository.impl[IO](xa)

      val carController = CarController[IO](carRepository)
      val insuranceController = InsuranceController[IO](aggregateRepository)

      for {
        _ <- FlywayMigration.migrate[IO](config.database)

        routes = List(
          carController,
          insuranceController
        ).flatMap(_.endpoints)

        httpApp = Http4sServerInterpreter[IO]().toRoutes(routes).orNotFound
        httpAppWithLogging = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

        port <- Sync[IO].fromOption(
          Port.fromInt(config.http.port),
          new RuntimeException("Invalid http port")
        )

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
