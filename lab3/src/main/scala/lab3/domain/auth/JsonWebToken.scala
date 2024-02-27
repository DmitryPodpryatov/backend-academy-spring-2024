package lab3.domain.auth

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

final case class JsonWebToken(
    header: JwtHeader,
    payload: JwtPayload,
    signature: JwtSignature
)

object JsonWebToken {
  implicit val encoder: Encoder[JsonWebToken] = deriveEncoder[JsonWebToken]
  implicit val decoder: Decoder[JsonWebToken] = deriveDecoder[JsonWebToken]

  implicit val schema: Schema[JsonWebToken] = Schema.derived[JsonWebToken]
}
