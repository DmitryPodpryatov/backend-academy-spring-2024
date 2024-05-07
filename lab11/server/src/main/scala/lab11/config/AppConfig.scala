package lab11.config

case class AppConfig(
    http: HttpServer,
    database: PostgresConfig
)
