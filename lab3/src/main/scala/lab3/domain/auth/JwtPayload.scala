package lab3.domain.auth

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class JwtPayload()

object JwtPayload {
  implicit val encoder: Encoder[JwtPayload] = deriveEncoder[JwtPayload]
  implicit val decoder: Decoder[JwtPayload] = deriveDecoder[JwtPayload]

  implicit val schema: Schema[JwtPayload] = Schema.derived[JwtPayload]
}
