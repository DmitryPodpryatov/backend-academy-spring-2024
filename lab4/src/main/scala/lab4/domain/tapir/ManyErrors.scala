package lab4.domain.tapir

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

sealed trait ManyErrors extends Throwable

object ManyErrors {
  final case class BadRequest(errorDetails: Map[String, String]) extends ManyErrors
  object BadRequest {
    implicit val encoder: Encoder[BadRequest] = deriveEncoder
    implicit val decoder: Decoder[BadRequest] = deriveDecoder
    implicit val schema: Schema[BadRequest] = Schema.derived
  }

  final case class Unauthorized(realm: String) extends ManyErrors
  object Unauthorized {
    implicit val encoder: Encoder[Unauthorized] = deriveEncoder
    implicit val decoder: Decoder[Unauthorized] = deriveDecoder
    implicit val schema: Schema[Unauthorized] = Schema.derived
  }

  final case class Forbidden(errorMessage: String) extends ManyErrors
  object Forbidden {
    implicit val encoder: Encoder[Forbidden] = deriveEncoder
    implicit val decoder: Decoder[Forbidden] = deriveDecoder
    implicit val schema: Schema[Forbidden] = Schema.derived
  }

  final case class NotFound(id: String) extends ManyErrors
  object NotFound {
    implicit val encoder: Encoder[NotFound] = deriveEncoder
    implicit val decoder: Decoder[NotFound] = deriveDecoder
    implicit val schema: Schema[NotFound] = Schema.derived
  }

  final case class InternalError(errorId: String, errorMessage: String) extends ManyErrors
  object InternalError {
    implicit val encoder: Encoder[InternalError] = deriveEncoder
    implicit val decoder: Decoder[InternalError] = deriveDecoder
    implicit val schema: Schema[InternalError] = Schema.derived
  }

  final case object Unexpected extends ManyErrors {
    implicit val encoder: Encoder[Unexpected.type] = deriveEncoder
    implicit val decoder: Decoder[Unexpected.type] = deriveDecoder
    implicit val schema: Schema[Unexpected.type] = Schema.derived
  }
}
