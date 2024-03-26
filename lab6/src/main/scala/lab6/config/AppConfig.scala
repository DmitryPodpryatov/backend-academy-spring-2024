package lab6.config

case class AppConfig(
    http: HttpServer,
    database: PostgresConfig
)
