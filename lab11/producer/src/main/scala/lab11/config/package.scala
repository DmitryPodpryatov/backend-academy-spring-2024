package lab11

import scala.concurrent.duration.FiniteDuration

package object config {

  case class AppConfig(
      kafka: KafkaConfig
  )

  final case class KafkaConfig(
      producer: ProducerConfig
  )

  final case class ProducerConfig(
      bootstrapServers: String,
      topic: String,
      producerDelay: FiniteDuration
  )

}
