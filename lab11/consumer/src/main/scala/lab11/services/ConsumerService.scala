package lab11.services

import cats.effect.{Async, Resource}
import cats.syntax.flatMap._
import cats.syntax.functor._
import fs2.kafka._
import io.circe.syntax._
import lab11.config.ConsumerConfig
import lab11.models.Event
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._

object ConsumerService {

  type ConsumerService[F[_]] = KafkaConsumer[F, Option[String], Event]

  def apply[F[_]: Async](
      config: ConsumerConfig
  ): Resource[F, ConsumerService[F]] = {
    val consumerSettings = ConsumerSettings[F, Option[String], Event]
      .withBootstrapServers(config.bootstrapServers)
      .withGroupId(config.groupId)
      .withEnableAutoCommit(true)
      .withAutoOffsetReset(AutoOffsetReset.Earliest)

    KafkaConsumer.resource(consumerSettings)

  }

  final implicit class ConsumerOps[F[_]](
      val consumer: ConsumerService[F]
  ) extends AnyVal {
    def run(config: ConsumerConfig)(implicit
        async: Async[F],
        logger: Logger[F]
    ): F[Unit] = {
      for {
        _ <- consumer.subscribeTo(config.topic)
        _ <- consumer
          .stream
          .evalMap { committableConsumerRecord =>
            val event = committableConsumerRecord.record.value

            processRecords(event)
          }
          .compile
          .drain
      } yield ()
    }
  }

  def processRecords[F[_]: Logger](event: Event): F[Unit] = {
    info"consumed event: ${event.asJson.noSpaces}"
  }

}
