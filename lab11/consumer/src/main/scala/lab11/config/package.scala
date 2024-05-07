package lab11

package object config {

  final case class AppConfig(
      kafka: KafkaConfig
  )

  final case class KafkaConfig(
      consumer: ConsumerConfig
  )

  final case class ConsumerConfig(
      bootstrapServers: String,
      topic: String,
      groupId: String
  )

}
