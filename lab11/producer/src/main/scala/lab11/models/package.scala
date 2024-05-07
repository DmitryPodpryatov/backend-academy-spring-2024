package lab11

import cats.effect.Sync
import fs2.kafka.{Deserializer, Serializer}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

import java.nio.charset.StandardCharsets
import java.util.UUID

package object models {

  final case class Event(id: UUID, n: Long)

  object Event {
    implicit val encoder: Encoder[Event] = deriveEncoder[Event]
    implicit val decoder: Decoder[Event] = deriveDecoder[Event]

    implicit def serializer[F[_]: Sync]: Serializer[F, Event] =
      Serializer.lift[F, Event] { event =>
        Sync[F].pure(event.asJson.noSpaces.getBytes(StandardCharsets.UTF_8))
      }

    implicit def deserializer[F[_]: Sync]: Deserializer[F, Event] =
      Deserializer.string[F]
        .map { s =>
          io.circe.parser.decode[Event](s) match {
            case Left(e)      => throw new RuntimeException(e.getMessage)
            case Right(event) => event
          }
        }
        .suspend
  }

}
