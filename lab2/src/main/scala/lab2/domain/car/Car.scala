package lab2.domain.car

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class Car()

object Car {
  implicit val encoder: Encoder[Car] = deriveEncoder[Car]
  implicit val decoder: Decoder[Car] = deriveDecoder[Car]

  implicit val schema: Schema[Car] = Schema.derived[Car]
}
