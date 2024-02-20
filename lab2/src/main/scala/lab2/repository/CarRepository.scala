package lab2.repository

import cats.effect.kernel.MonadCancelThrow
import doobie.Transactor
import doobie.implicits._
import doobie.postgres.implicits._
import lab2.domain.car.Car

import java.util.UUID

trait CarRepository[F[_]] {
  def create(car: Car): F[Int]

  def find(id: UUID): F[Option[Car]]

  def delete(id: UUID): F[Option[Car]]
}

object CarRepository {

  final private class Impl[F[_]: MonadCancelThrow](xa: Transactor[F])
      extends CarRepository[F] {
    override def create(car: Car): F[Int] = ???

    override def find(id: UUID): F[Option[Car]] = ???

    override def delete(id: UUID): F[Option[Car]] = ???
  }

  def impl[F[_]: MonadCancelThrow](xa: Transactor[F]): CarRepository[F] = new Impl[F](xa)

}
