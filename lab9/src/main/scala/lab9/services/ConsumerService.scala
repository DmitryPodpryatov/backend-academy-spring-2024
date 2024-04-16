package lab9.services

import cats.effect.{Async, Resource}
import fs2.kafka._
import lab9.config.KafkaConfig
import org.typelevel.log4cats.Logger

object ConsumerService {

  type ConsumerService[F[_]] = KafkaConsumer[F, String, String]

  def apply[F[_]: Async: Logger](
      config: KafkaConfig
  ): Resource[F, ConsumerService[F]] = {
    ???
  }

  final implicit class ConsumerOps[F[_]](
      val consumer: ConsumerService[F]
  ) extends AnyVal {
    def run(config: KafkaConfig)(implicit
        async: Async[F],
        logger: Logger[F]
    ): F[Unit] = {
      ???
    }
  }

  def processRecords[F[_]: Logger](key: String, value: String): F[Unit] = {
    ???
  }

}
