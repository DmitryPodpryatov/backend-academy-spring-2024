package lab8

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

import java.time.Instant

package object domain {

  final case class Application(
      id: String,
      userId: String,
      currency: String,
      amount: BigDecimal,
      timestamp: Instant
  )

  object Application {
    implicit val encoder: Encoder[Application] = deriveEncoder[Application]
    implicit val decoder: Decoder[Application] = deriveDecoder[Application]
    implicit val schema: Schema[Application] = Schema.derived[Application]
  }

}
