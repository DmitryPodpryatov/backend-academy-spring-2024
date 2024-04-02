package lab7.domain

import java.time.Instant
import scala.concurrent.duration.FiniteDuration

package object cb {

  sealed trait Reason

  sealed trait State
  object State {

    final case class Closed(failures: Int) extends State
    object Closed {
      val Zero: Closed = Closed(0)
    }

    final case class Open(startedAt: Instant, resetTimeout: FiniteDuration)
        extends State with Reason {
      val expiresAt: Instant = startedAt.plusSeconds(resetTimeout.toSeconds)
    }

    final case object HalfOpen extends State with Reason

  }

  final case class RejectedExecution(reason: Reason)
      extends RuntimeException(s"Execution rejected: $reason")

}
