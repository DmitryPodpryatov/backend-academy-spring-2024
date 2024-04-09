package lab8.services

import cats.effect.{Async, Resource}
import fs2.kafka.KafkaProducer
import lab8.config.KafkaConfig
import lab8.domain.Application

trait ProducerService[F[_]] {
  def produce(application: Application): F[Unit]
}

object ProducerService {

  final private class Impl[F[_]: Async](
      producer: KafkaProducer[F, String, String],
      config: KafkaConfig
  ) extends ProducerService[F] {
    override def produce(application: Application): F[Unit] = {
      ???
    }
  }

  def apply[F[_]: Async](config: KafkaConfig): Resource[F, ProducerService[F]] = {
    ???
  }

}
