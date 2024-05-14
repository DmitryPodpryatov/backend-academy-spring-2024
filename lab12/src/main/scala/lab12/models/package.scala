package lab12

import doobie.Meta
import doobie.postgres.implicits._
import enumeratum.values.{StringDoobieEnum, StringEnum, StringEnumEntry}
import io.estatico.newtype.macros.newtype

import java.time.Instant
import java.util.UUID

package object models {

  final case class OutboxEvent(eventId: EventId, status: EventStatus, createdAt: Instant)

  @newtype final case class EventId(value: UUID)
  object EventId {
    implicit val meta: Meta[EventId] = Meta[UUID].timap(apply)(_.value)
  }

  sealed abstract class EventStatus(override val value: String) extends StringEnumEntry
  object EventStatus
      extends StringEnum[EventStatus]
      with StringDoobieEnum[EventStatus] {

    case object InProgress extends EventStatus("IN_PROGRESS")
    case object Done extends EventStatus("DONE")

    override def values: IndexedSeq[EventStatus] = findValues
  }

}
