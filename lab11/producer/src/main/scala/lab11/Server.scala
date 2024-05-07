package lab11

import lab11.config.AppConfig
import lab11.services.ProducerService
import lab11.services.ProducerService._
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
      producer <- ProducerService[IO](config.kafka.producer)
    } yield producer).use { producer =>
      producer.run(config.kafka.producer)
    }
  }
}
