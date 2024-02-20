package lab2.repository

import cats.effect.kernel.MonadCancelThrow
import doobie.Transactor

trait AggregateRepository[F[_]] {}

object AggregateRepository {

  final private class Impl[F[_]: MonadCancelThrow](xa: Transactor[F])
      extends AggregateRepository[F] {}

  def impl[F[_]: MonadCancelThrow](xa: Transactor[F]): AggregateRepository[F] = new Impl[F](xa)

}
