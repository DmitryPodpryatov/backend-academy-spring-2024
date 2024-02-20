package lab2.domain.car.error

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

import scala.util.control.NoStackTrace

case class CarError(errorCode: String, errorMessage: String)
    extends Throwable(errorMessage)
    with NoStackTrace

object CarError {
  implicit val encoder: Encoder[CarError] = deriveEncoder[CarError]
  implicit val decoder: Decoder[CarError] = deriveDecoder[CarError]

  implicit val schema: Schema[CarError] = Schema.derived[CarError]
}
