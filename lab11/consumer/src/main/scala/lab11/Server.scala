package lab11

import lab11.config.AppConfig
import lab11.services.ConsumerService
import lab11.services.ConsumerService._
import cats.effect.IO
import org.typelevel.log4cats.slf4j._
import org.typelevel.log4cats.{Logger, LoggerFactory}
import pureconfig.ConfigSource
import pureconfig.generic.auto.exportReader

object Server {

  def run: IO[Unit] = {
    val config = ConfigSource.default.loadOrThrow[AppConfig]

    implicit val logger: Logger[IO] = LoggerFactory[IO].getLogger

    (for {
      consumer <- ConsumerService[IO](config.kafka.consumer)
    } yield consumer).use { consumer =>
      consumer.run(config.kafka.consumer)
    }
  }
}
