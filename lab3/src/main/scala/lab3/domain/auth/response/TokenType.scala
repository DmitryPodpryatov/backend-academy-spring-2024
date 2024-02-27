package lab3.domain.auth.response

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.Schema
import sttp.tapir.codec.enumeratum.TapirCodecEnumeratum

sealed abstract class TokenType(val value: String) extends StringEnumEntry

object TokenType
    extends StringEnum[TokenType]
    with StringCirceEnum[TokenType]
    with TapirCodecEnumeratum {

  final case object Bearer extends TokenType("Bearer")

  override def values: IndexedSeq[TokenType] = findValues

  implicit val schema: Schema[TokenType] = schemaForStringEnumEntry[TokenType]
  implicit val codec: PlainCodec[TokenType] = plainCodecStringEnumEntry[TokenType]

}
