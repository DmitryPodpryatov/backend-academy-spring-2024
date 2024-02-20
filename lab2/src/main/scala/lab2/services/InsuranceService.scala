package lab2.services

import lab2.repository.AggregateRepository

trait InsuranceService[F[_]] {

}

object InsuranceService {

  final private class Impl[F[_]](
      aggregateRepository: AggregateRepository[F]
  ) extends InsuranceService[F] {

  }

  def impl[F[_]](
      aggregateRepository: AggregateRepository[F]
  ): InsuranceService[F] = new Impl[F](aggregateRepository)

}
