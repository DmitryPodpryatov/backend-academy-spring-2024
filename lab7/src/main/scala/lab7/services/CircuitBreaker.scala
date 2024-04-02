package lab7.services

import cats.effect.Ref
import lab7.domain.cb._

trait CircuitBreaker[F[_]] {
  def protect[A](fa: F[A]): F[A]
}

object CircuitBreaker {

  final private class Impl[F[_]](
      ref: Ref[F, State]
  ) extends CircuitBreaker[F] {
    override def protect[A](fa: F[A]): F[A] = ???
  }

  def build[F[_]]: F[CircuitBreaker[F]] = {
    ???
  }

}
