package lab3.domain.auth

import cats.syntax.option._
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype
import sttp.tapir.{Codec, CodecFormat, Schema}

package object response {

  @newtype
  final case class AccessToken(id: String)
  object AccessToken {
    implicit val encoder: Encoder[AccessToken] = Encoder[String].contramap(_.id)
    implicit val decoder: Decoder[AccessToken] = Decoder[String].map(apply)

    implicit val schema: Schema[AccessToken] = Schema.schemaForString.map(apply(_).some)(_.id)
    implicit val codec: Codec[String, AccessToken, CodecFormat.TextPlain] =
      Codec.string.map[AccessToken](apply _)(_.id)
  }

}
