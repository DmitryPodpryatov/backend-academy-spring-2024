package lab2.config

case class AppConfig(database: PostgresConfig, http: HttpServer)
