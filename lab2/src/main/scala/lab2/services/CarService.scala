package lab2.services

import lab2.domain.car.{Car, CarId}
import lab2.domain.car.error.CarError
import lab2.repository.CarRepository

trait CarService[F[_]] {
  def create(car: Car): F[Either[CarError, CarId]]
  def find(id: CarId): F[Either[CarError, Option[Car]]]
  def delete(id: CarId): F[Either[CarError, Car]]
}

object CarService {

  final private class Impl[F[_]](carRepository: CarRepository[F]) extends CarService[F] {
    override def create(car: Car): F[Either[CarError, CarId]] = ???

    override def find(id: CarId): F[Either[CarError, Option[Car]]] = ???

    override def delete(id: CarId): F[Either[CarError, Car]] = ???
  }

  def impl[F[_]](carRepository: CarRepository[F]): CarService[F] = new Impl[F](carRepository)

}
