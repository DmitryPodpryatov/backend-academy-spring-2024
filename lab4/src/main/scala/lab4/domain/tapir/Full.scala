package lab4.domain.tapir

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

sealed trait FullError extends Throwable
object FullError {
  final case object A extends FullError {
    implicit val encoder: Encoder[A.type] = deriveEncoder
    implicit val decoder: Decoder[A.type] = deriveDecoder
    implicit val schema: Schema[A.type] = Schema.derived
  }

  final case object B extends FullError {
    implicit val encoder: Encoder[B.type] = deriveEncoder
    implicit val decoder: Decoder[B.type] = deriveDecoder
    implicit val schema: Schema[B.type] = Schema.derived
  }
}

sealed trait FullResponse
object FullResponse {
  final case object C extends FullResponse {
    implicit val encoder: Encoder[C.type] = deriveEncoder
    implicit val decoder: Decoder[C.type] = deriveDecoder
    implicit val schema: Schema[C.type] = Schema.derived
  }

  final case object D extends FullResponse {
    implicit val encoder: Encoder[D.type] = deriveEncoder
    implicit val decoder: Decoder[D.type] = deriveDecoder
    implicit val schema: Schema[D.type] = Schema.derived
  }
}
