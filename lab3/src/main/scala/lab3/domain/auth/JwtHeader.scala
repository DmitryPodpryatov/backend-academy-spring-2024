package lab3.domain.auth

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class JwtHeader()

object JwtHeader {
  implicit val encoder: Encoder[JwtHeader] = deriveEncoder[JwtHeader]
  implicit val decoder: Decoder[JwtHeader] = deriveDecoder[JwtHeader]

  implicit val schema: Schema[JwtHeader] = Schema.derived[JwtHeader]
}
