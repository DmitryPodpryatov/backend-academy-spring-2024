package lab15

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.tapir.Schema

import java.util.UUID

package object models {

  final case class User(userId: UUID, name: String, email: String, address: Option[String])

  object User {
    implicit val encoder: Encoder[User] = deriveEncoder[User]
    implicit val decoder: Decoder[User] = deriveDecoder[User]

    implicit val schema: Schema[User] = Schema.derived[User]
  }

}
