package lab12.config

case class AppConfig(
    http: HttpServer,
    database: PostgresConfig
)
