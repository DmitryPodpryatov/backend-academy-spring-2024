package lab6.metrics

import cats.effect.Sync
import io.prometheus.client.CollectorRegistry

object Metrics {
  def register[F[_]: Sync](collectorRegistry: CollectorRegistry): F[Unit] = ???
}
