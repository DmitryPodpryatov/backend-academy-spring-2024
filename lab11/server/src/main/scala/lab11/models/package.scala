package lab11

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

import java.util.UUID

package object models {

  final case class Event(id: UUID, n: Long)

  object Event {
    implicit val encoder: Encoder[Event] = deriveEncoder[Event]
    implicit val decoder: Decoder[Event] = deriveDecoder[Event]

    implicit val schema: Schema[Event] = Schema.derived[Event]
  }

}
