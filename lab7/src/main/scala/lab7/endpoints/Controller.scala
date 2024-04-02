package lab7.endpoints

import sttp.tapir.server.ServerEndpoint

trait Controller[F[_]] {
  def endpoints: List[ServerEndpoint[Any, F]]
}
