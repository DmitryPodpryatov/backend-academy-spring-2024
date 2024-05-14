package lab12.services

import cats.effect.MonadCancelThrow
import doobie.Transactor
import doobie.implicits._
import doobie.postgres.implicits._
import lab12.models.{EventId, EventStatus, OutboxEvent}

trait PostgresClientService[F[_]] {
  def select: F[List[OutboxEvent]]
  def update(eventId: EventId, status: EventStatus): F[Int]
}

object PostgresClientService {

  final private class Impl[F[_]: MonadCancelThrow](xa: Transactor[F])
      extends PostgresClientService[F] {
    override def select: F[List[OutboxEvent]] = ???

    override def update(eventId: EventId, status: EventStatus): F[Int] = ???
  }

  def apply[F[_]: MonadCancelThrow](xa: Transactor[F]): PostgresClientService[F] = {
    new Impl(xa)
  }

}
