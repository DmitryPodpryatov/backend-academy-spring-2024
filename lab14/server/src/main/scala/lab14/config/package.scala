package lab14

package object config {

  case class AppConfig(http: HttpServer)

  final case class HttpServer(port: Int)

}
