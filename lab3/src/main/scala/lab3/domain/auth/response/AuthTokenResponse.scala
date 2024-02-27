package lab3.domain.auth.response

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class AuthTokenResponse(
    tokenType: TokenType,
    expiresIn: Long,
    accessToken: AccessToken
)

object AuthTokenResponse {
  implicit val encoder: Encoder[AuthTokenResponse] = deriveEncoder[AuthTokenResponse]
  implicit val decoder: Decoder[AuthTokenResponse] = deriveDecoder[AuthTokenResponse]

  implicit val schema: Schema[AuthTokenResponse] = Schema.derived[AuthTokenResponse]
}
