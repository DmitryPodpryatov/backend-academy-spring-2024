package lab2.domain

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype
import sttp.tapir.{Codec, CodecFormat, Schema}

import java.util.UUID

package object car {
  @newtype
  final case class CarId(id: UUID)

  object CarId {
    implicit val encoder: Encoder[CarId] = Encoder[UUID].contramap(_.id)
    implicit val decoder: Decoder[CarId] = Decoder[UUID].map(apply)

    implicit val schema: Schema[CarId] = Schema.schemaForUUID.map(uuid => Some(apply(uuid)))(_.id)
    implicit val codec: Codec[String, CarId, CodecFormat.TextPlain] =
      Codec.uuid.map[CarId](apply _)(_.id)
  }
}
