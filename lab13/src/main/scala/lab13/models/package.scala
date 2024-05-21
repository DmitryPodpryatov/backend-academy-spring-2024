package lab13

import doobie.postgres.implicits._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
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
