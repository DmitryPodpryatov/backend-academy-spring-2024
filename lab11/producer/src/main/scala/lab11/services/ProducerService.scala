package lab11.services

import cats.effect.{Async, Resource, Temporal}
import cats.syntax.flatMap._
import cats.syntax.functor._
import fs2.kafka.{KafkaProducer, ProducerSettings}
import io.circe.syntax._
import lab11.config.ProducerConfig
import lab11.models.Event
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._

import java.util.UUID

object ProducerService {

  type ProducerService[F[_]] = KafkaProducer[F, Option[String], Event]

  def apply[F[_]: Async](config: ProducerConfig): Resource[F, ProducerService[F]] = {
    val producerSettings =
      ProducerSettings[F, Option[String], Event]
        .withBootstrapServers(config.bootstrapServers)

    for {
      kafkaProducer <- KafkaProducer.resource(producerSettings)
    } yield kafkaProducer
  }

  final implicit class ProducerOps[F[_]](
      val producer: KafkaProducer[F, Option[String], Event]
  ) extends AnyVal {
    def run(
        config: ProducerConfig
    )(implicit
        async: Async[F],
        temporal: Temporal[F],
        logger: Logger[F]
    ): F[Unit] = {

      fs2.Stream.iterate(0L)(_ + 1)
        .metered[F](config.producerDelay)
        .map { n =>
          val uuid = UUID.randomUUID()

          Event(uuid, n)
        }
        .evalTap { event =>
          info"producing event ${event.asJson.noSpaces}"
        }
        .evalMap { event =>
          producer.produceOne(
            topic = config.topic,
            key = None,
            value = event
          )
            .flatten
            .void
        }
        .compile
        .drain
    }
  }

}
