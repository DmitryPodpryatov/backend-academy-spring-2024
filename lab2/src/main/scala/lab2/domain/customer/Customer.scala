package lab2.domain.customer

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class Customer()

object Customer {
  implicit val encoder: Encoder[Customer] = deriveEncoder[Customer]
  implicit val decoder: Decoder[Customer] = deriveDecoder[Customer]

  implicit val schema: Schema[Customer] = Schema.derived[Customer]
}
