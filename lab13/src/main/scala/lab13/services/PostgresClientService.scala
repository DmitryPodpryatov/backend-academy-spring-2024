package lab13.services

import cats.effect.MonadCancelThrow
import doobie.Transactor
import doobie.implicits._
import doobie.postgres.implicits._
import lab13.models.User

import java.util.UUID

trait PostgresClientService[F[_]] {
  def insert(user: User): F[Int]
  def select(id: UUID): F[Option[User]]
  def update(user: User): F[Option[User]]
}

object PostgresClientService {

  final private class Impl[F[_]: MonadCancelThrow](xa: Transactor[F])
      extends PostgresClientService[F] {

    override def insert(user: User): F[Int] =
      sql"""
            insert into users (user_id, "name", email, address)
            values (${user.userId}, ${user.name}, ${user.email}, ${user.address})
         """
        .update
        .run
        .transact(xa)

    override def select(id: UUID): F[Option[User]] =
      sql"""
            select *
            from users
            where user_id = $id
         """
        .query[User]
        .option
        .transact(xa)

    override def update(user: User): F[Option[User]] =
      sql"""
            update users
            set user_id = ${user.userId},
                "name" =  ${user.name}, 
                email = ${user.email},
                address = ${user.address}
            where user_id = ${user.userId}
            returning *
         """
        .query[User]
        .option
        .transact(xa)
  }

  def apply[F[_]: MonadCancelThrow](xa: Transactor[F]): PostgresClientService[F] = {
    new Impl(xa)
  }

}
