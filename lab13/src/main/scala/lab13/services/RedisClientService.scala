package lab13.services

import cats.Monad
import lab13.database.RedisClient
import lab13.models.User

import java.util.UUID

trait RedisClientService[F[_]] {
  def set(user: User): F[Unit]
  def set(user: User, ttl: Long): F[Unit]
  def get(userId: UUID): F[Option[User]]
}

object RedisClientService {

  final private class Impl[F[_]: Monad](client: RedisClient[F]) extends RedisClientService[F] {
    override def set(user: User): F[Unit] = {
      ???
    }

    override def set(user: User, ttl: Long): F[Unit] = {
      ???
    }

    override def get(userId: UUID): F[Option[User]] = {
      ???
    }
  }

  def apply[F[_]: Monad](client: RedisClient[F]): RedisClientService[F] = new Impl[F](client)

}
