package lab10.services

import cats.effect.{Async, Resource}
import cats.syntax.functor._
import cats.syntax.flatMap._
import fs2.kafka._
import lab10.config.KafkaConfig

object ConsumerService {

  type ConsumerService[F[_]] = KafkaConsumer[F, String, String]

  def apply[F[_]: Async](
      config: KafkaConfig
  ): Resource[F, ConsumerService[F]] = {
    val consumerSettings =
      ConsumerSettings[F, String, String]
        .withAutoOffsetReset(AutoOffsetReset.Earliest)
        .withBootstrapServers(config.url)
        .withGroupId(config.groupId)

    KafkaConsumer.resource(consumerSettings)
  }

  final implicit class ConsumerOps[F[_]](
      val consumer: ConsumerService[F]
  ) extends AnyVal {
    def run(config: KafkaConfig)(implicit
        async: Async[F]
    ): F[Unit] = {
      for {
        _ <- consumer.subscribeTo(config.topic)
        _ <- consumer
          .stream
          .evalMap { committableConsumerRecord =>
            val key = committableConsumerRecord.record.key
            val value = committableConsumerRecord.record.value

            processRecords(key, value)
              .as(committableConsumerRecord.offset)
          }
          .through(commitBatchWithin(config.batch, config.timeWindow))
          .compile
          .drain
      } yield ()
    }
  }

  def processRecords[F[_]: Async](key: String, value: String): F[Unit] = {
    ???
  }

}
