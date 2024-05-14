package lab12

import cats.effect.{Async, IO}
import lab12.config.AppConfig
import lab12.database.{FlywayMigration, makeTransactor}
import lab12.services.{PollingService, PostgresClientService}
import org.typelevel.log4cats.slf4j._
import org.typelevel.log4cats.{Logger, LoggerFactory}
import pureconfig.ConfigSource
import pureconfig.generic.auto.exportReader

object Server {

  def run: IO[Unit] = {
    val config = ConfigSource.default.loadOrThrow[AppConfig]

    implicit val logger: Logger[IO] = LoggerFactory[IO].getLogger

    makeTransactor[IO](config.database).use { implicit transactor =>
      for {
        _ <- FlywayMigration.clear[IO](config.database)
        _ <- FlywayMigration.migrate[IO](config.database)

        postgresService = PostgresClientService[IO](transactor)
        _ = ???
      } yield ()
    }
  }

}
