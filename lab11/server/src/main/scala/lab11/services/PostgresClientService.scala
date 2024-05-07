package lab11.services

import cats.effect.MonadCancelThrow
import doobie.implicits._
import doobie.postgres.implicits._
import doobie.util.transactor.Transactor
import lab11.models.Event

import java.util.UUID

trait PostgresClientService[F[_]] {
  def insert(event: Event): F[Int]
  def select(id: UUID): F[Option[Event]]
}

object PostgresClientService {

  final private class Impl[F[_]: MonadCancelThrow](xa: Transactor[F])
      extends PostgresClientService[F] {
    override def insert(event: Event): F[Int] =
      sql"""
            insert into event(id, n)
            values (${event.id}, ${event.n})
         """
        .update
        .run
        .transact(xa)

    override def select(id: UUID): F[Option[Event]] =
      sql"""
            select *
            from event
            where id = $id
         """
        .query[Event]
        .option
        .transact(xa)
  }

  def apply[F[_]: MonadCancelThrow](xa: Transactor[F]): PostgresClientService[F] = new Impl[F](xa)

}
