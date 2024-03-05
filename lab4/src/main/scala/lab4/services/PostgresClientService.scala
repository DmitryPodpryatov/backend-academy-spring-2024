package lab4.services

import cats.effect.MonadCancelThrow
import cats.syntax.applicativeError._
import cats.syntax.either._
import cats.syntax.functor._
import doobie.implicits._
import doobie.util.transactor.Transactor
import lab4.domain.postgres.PostgresError

import java.sql.SQLException

trait PostgresClientService[F[_]] {
  def insert(id: String): F[Either[PostgresError, Int]]
}

object PostgresClientService {

  final private class Impl[F[_]: MonadCancelThrow](xa: Transactor[F])
      extends PostgresClientService[F] {
    override def insert(id: String): F[Either[PostgresError, Int]] =
      sql"""
            insert into example(id)
            values ($id)
         """
        .update
        .run
        .transact(xa)
        .map(_.asRight[PostgresError])
        .recover {
          // recover from SQLException
          ???
        }
  }

  def impl[F[_]: MonadCancelThrow](xa: Transactor[F]): PostgresClientService[F] = new Impl[F](xa)

}
