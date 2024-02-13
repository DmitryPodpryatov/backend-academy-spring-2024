package lab1.payments.response

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class TransferByPhoneResponse()

object TransferByPhoneResponse {
  implicit val encoder: Encoder[TransferByPhoneResponse] = deriveEncoder[TransferByPhoneResponse]
  implicit val decoder: Decoder[TransferByPhoneResponse] = deriveDecoder[TransferByPhoneResponse]

  implicit val schema: Schema[TransferByPhoneResponse] = Schema.derived[TransferByPhoneResponse]
}
