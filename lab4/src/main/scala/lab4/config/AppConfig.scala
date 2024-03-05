package lab4.config

case class AppConfig(
    http: HttpServer,
    database: PostgresConfig
)
