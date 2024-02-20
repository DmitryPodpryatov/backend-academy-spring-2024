package lab2.config

final case class PostgresConfig(url: String, user: String, password: String, poolSize: Int)
