package lab3.domain.auth

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class JwtSignature()

object JwtSignature {
  implicit val encoder: Encoder[JwtSignature] = deriveEncoder[JwtSignature]
  implicit val decoder: Decoder[JwtSignature] = deriveDecoder[JwtSignature]

  implicit val schema: Schema[JwtSignature] = Schema.derived[JwtSignature]
}
