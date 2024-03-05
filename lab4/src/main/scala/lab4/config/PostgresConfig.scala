package lab4.config

final case class PostgresConfig(url: String, user: String, password: String, poolSize: Int)
