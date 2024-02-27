package lab3.domain.auth.errors

import cats.syntax.functor._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import sttp.tapir.Schema

sealed abstract class AuthError(val errorCode: String, val errorMessage: String)
    extends Throwable(errorMessage)

object AuthError {

  final case class InternalError(
      override val errorCode: String = "INTERNAL_ERROR",
      override val errorMessage: String = "Непредвиденная ошибка"
  ) extends AuthError(errorCode, errorMessage)

  object InternalError {
    implicit val encoder: Encoder[InternalError] = deriveEncoder
    implicit val decoder: Decoder[InternalError] = deriveDecoder
  }

  implicit val encoder: Encoder[AuthError] = Encoder.instance[AuthError] {
    case ie: InternalError => ie.asJson
  }
  implicit val decoder: Decoder[AuthError] = {
    List[Decoder[AuthError]](
      Decoder[InternalError].widen
    ).reduceLeft(_ or _)
  }

  implicit val schema: Schema[AuthError] = Schema.derived[AuthError]
}
