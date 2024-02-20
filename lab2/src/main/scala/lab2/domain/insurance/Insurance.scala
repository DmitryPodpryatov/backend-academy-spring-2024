package lab2.domain.insurance

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class Insurance()

object Insurance {
  implicit val encoder: Encoder[Insurance] = deriveEncoder[Insurance]
  implicit val decoder: Decoder[Insurance] = deriveDecoder[Insurance]

  implicit val schema: Schema[Insurance] = Schema.derived[Insurance]
}
