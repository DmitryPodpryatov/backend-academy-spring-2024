package lab9.config

import scala.concurrent.duration.FiniteDuration

final case class KafkaConfig(
    url: String,
    topic: String,
    groupId: String,
    batch: Int,
    timeWindow: FiniteDuration
)
