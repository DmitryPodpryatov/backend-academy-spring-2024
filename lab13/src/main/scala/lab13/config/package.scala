package lab13

package object config {

  case class AppConfig(
      http: HttpServer,
      postgres: PostgresConfig,
      redis: RedisConfig
  )

  final case class HttpServer(port: Int)

  final case class PostgresConfig(url: String, user: String, password: String, poolSize: Int)

  final case class RedisConfig(url: String)

}
