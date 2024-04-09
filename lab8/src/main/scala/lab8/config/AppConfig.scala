package lab8.config

case class AppConfig(
    http: HttpServer,
    kafka: KafkaConfig
)
