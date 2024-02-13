package lab1.payments.errors

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

import scala.util.control.NoStackTrace

case class PaymentsError(errorCode: Int, errorMessage: String)
    extends Throwable(errorMessage)
    with NoStackTrace

object PaymentsError {
  implicit val encoder: Encoder[PaymentsError] = deriveEncoder[PaymentsError]
  implicit val decoder: Decoder[PaymentsError] = deriveDecoder[PaymentsError]

  implicit val schema: Schema[PaymentsError] = Schema.derived[PaymentsError]
}
