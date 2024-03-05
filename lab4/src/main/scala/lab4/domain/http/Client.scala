package lab4.domain.http

import cats.syntax.functor._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

sealed trait ClientError extends Throwable
object ClientError {
  final case object TooManyRequests extends ClientError {
    implicit val encoder: Encoder[TooManyRequests.type] = deriveEncoder
    implicit val decoder: Decoder[TooManyRequests.type] = deriveDecoder
  }

  final case class InternalError(errorId: String, errorMessage: String) extends ClientError
  object InternalError {
    implicit val encoder: Encoder[InternalError] = deriveEncoder
    implicit val decoder: Decoder[InternalError] = deriveDecoder
  }

  implicit val encoder: Encoder[ClientError] = Encoder.instance {
    case TooManyRequests          => TooManyRequests.asJson
    case ie @ InternalError(_, _) => ie.asJson
  }

  implicit val decoder: Decoder[ClientError] =
    List[Decoder[ClientError]](
      Decoder[TooManyRequests.type].widen,
      Decoder[InternalError].widen
    ).reduceLeft(_ or _)
}

sealed trait ClientResponse
object ClientResponse {
  final case class Ok(id: String, version: Int) extends ClientResponse
  object Ok {
    implicit val encoder: Encoder[Ok] = deriveEncoder
    implicit val decoder: Decoder[Ok] = deriveDecoder
  }

  final case class Created(ref: String) extends ClientResponse
  object Created {
    implicit val encoder: Encoder[Created] = deriveEncoder
    implicit val decoder: Decoder[Created] = deriveDecoder
  }

  implicit val encoder: Encoder[ClientResponse] = Encoder.instance {
    case ok: Ok           => ok.asJson
    case created: Created => created.asJson
  }

  implicit val decoder: Decoder[ClientResponse] =
    List[Decoder[ClientResponse]](
      Decoder[Ok].widen,
      Decoder[Created].widen
    ).reduceLeft(_ or _)
}
