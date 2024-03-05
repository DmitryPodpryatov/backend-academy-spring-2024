package lab4.domain.postgres

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

final case class PostgresError(code: String, cause: String) extends Throwable

object PostgresError {
  implicit val encoder: Encoder[PostgresError] = deriveEncoder
  implicit val decoder: Decoder[PostgresError] = deriveDecoder
  implicit val schema: Schema[PostgresError] = Schema.derived
}
