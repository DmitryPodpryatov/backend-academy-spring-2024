package lab10.services

import cats.effect.Sync
import doobie.Transactor

trait PostgresClientService[F[_]] {
  def uploadFile: F[Int]
}

object PostgresClientService {

  final private class Impl[F[_]: Sync](xa: Transactor[F])
      extends PostgresClientService[F] {
    override def uploadFile: F[Int] = ???
  }

  def apply[F[_]: Sync](xa: Transactor[F]): PostgresClientService[F] = {
    new Impl(xa)
  }

}
