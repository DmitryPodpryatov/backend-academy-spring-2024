package lab7.services

import scala.concurrent.duration.FiniteDuration

object Retry {

  type Backoff = FiniteDuration => FiniteDuration

  def retry[F[_], E, A](
      fa: F[Either[E, A]],
      delay: FiniteDuration,
      retries: Int,
      strategy: E => Boolean
  ): F[Either[E, A]] = ???

}
