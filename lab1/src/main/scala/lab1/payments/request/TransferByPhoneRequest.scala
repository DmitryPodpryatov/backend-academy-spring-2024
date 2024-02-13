package lab1.payments.request

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class TransferByPhoneRequest()

object TransferByPhoneRequest {
  implicit val encoder: Encoder[TransferByPhoneRequest] = deriveEncoder[TransferByPhoneRequest]
  implicit val decoder: Decoder[TransferByPhoneRequest] = deriveDecoder[TransferByPhoneRequest]

  implicit val schema: Schema[TransferByPhoneRequest] = Schema.derived[TransferByPhoneRequest]
}
