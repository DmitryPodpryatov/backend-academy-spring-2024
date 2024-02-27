package lab3.domain.auth

import cats.syntax.option._
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype
import sttp.tapir.{Codec, CodecFormat, Schema}

package object request {

  @newtype
  final case class ClientId(id: String)
  object ClientId {
    implicit val encoder: Encoder[ClientId] = Encoder[String].contramap(_.id)
    implicit val decoder: Decoder[ClientId] = Decoder[String].map(apply)

    implicit val schema: Schema[ClientId] = Schema.schemaForString.map(apply(_).some)(_.id)
    implicit val codec: Codec[String, ClientId, CodecFormat.TextPlain] =
      Codec.string.map[ClientId](apply _)(_.id)
  }

  @newtype
  final case class ClientSecret(id: String)
  object ClientSecret {
    implicit val encoder: Encoder[ClientSecret] = Encoder[String].contramap(_.id)
    implicit val decoder: Decoder[ClientSecret] = Decoder[String].map(apply)

    implicit val schema: Schema[ClientSecret] = Schema.schemaForString.map(apply(_).some)(_.id)
    implicit val codec: Codec[String, ClientSecret, CodecFormat.TextPlain] =
      Codec.string.map[ClientSecret](apply _)(_.id)
  }

  @newtype
  final case class Scope(id: String)
  object Scope {
    implicit val encoder: Encoder[Scope] = Encoder[String].contramap(_.id)
    implicit val decoder: Decoder[Scope] = Decoder[String].map(apply)

    implicit val schema: Schema[Scope] = Schema.schemaForString.map(apply(_).some)(_.id)
    implicit val codec: Codec[String, Scope, CodecFormat.TextPlain] =
      Codec.string.map[Scope](apply _)(_.id)
  }

}
