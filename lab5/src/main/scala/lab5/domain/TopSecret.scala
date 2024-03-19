package lab5.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class TopSecret(
    clientId: String,
    clientSecret: String
)

object TopSecret {
  implicit val encoder: Encoder[TopSecret] = deriveEncoder[TopSecret]
  implicit val decoder: Decoder[TopSecret] = deriveDecoder[TopSecret]
}
