package lab3.domain.auth.request

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.Schema
import sttp.tapir.codec.enumeratum.TapirCodecEnumeratum

sealed abstract class GrantType(val value: String) extends StringEnumEntry

object GrantType
    extends StringEnum[GrantType]
    with StringCirceEnum[GrantType]
    with TapirCodecEnumeratum {

  final case object ClientCredentials extends GrantType("client_credentials")

  override def values: IndexedSeq[GrantType] = findValues

  implicit val schema: Schema[GrantType] = schemaForStringEnumEntry[GrantType]
  implicit val codec: PlainCodec[GrantType] = plainCodecStringEnumEntry[GrantType]

}
