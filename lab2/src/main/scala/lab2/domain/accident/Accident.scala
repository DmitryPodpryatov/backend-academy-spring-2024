package lab2.domain.accident

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

final case class Accident()

object Accident {
  implicit val encoder: Encoder[Accident] = deriveEncoder[Accident]
  implicit val decoder: Decoder[Accident] = deriveDecoder[Accident]
}
