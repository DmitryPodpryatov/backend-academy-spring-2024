package lab15

package object config {

  case class AppConfig(http: HttpServer, database: PostgresConfig)

  final case class HttpServer(port: Int)

  final case class PostgresConfig(url: String, user: String, password: String, poolSize: Int)

}
