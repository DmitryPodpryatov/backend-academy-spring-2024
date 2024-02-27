package lab3.domain.auth.request

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

final case class AuthTokenRequest(
    clientId: ClientId,
    clientSecret: ClientSecret,
    scope: Scope,
    grantType: GrantType
)

object AuthTokenRequest {
  implicit val encoder: Encoder[AuthTokenRequest] = deriveEncoder[AuthTokenRequest]
  implicit val decoder: Decoder[AuthTokenRequest] = deriveDecoder[AuthTokenRequest]

  implicit val schema: Schema[AuthTokenRequest] = Schema.derived[AuthTokenRequest]
}
