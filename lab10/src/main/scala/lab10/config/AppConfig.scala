package lab10.config

case class AppConfig(
    http: HttpServer,
    database: PostgresConfig,
    kafka: KafkaConfig
)
