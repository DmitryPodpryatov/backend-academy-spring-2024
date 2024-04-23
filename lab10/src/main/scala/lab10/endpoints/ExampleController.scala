package lab10.endpoints

import cats.effect.Sync
import sttp.tapir.server.ServerEndpoint

final class ExampleController[F[_]: Sync]() extends Controller[F] {

  def download = ???

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    download
  )
}

object ExampleController {

  def apply[F[_]: Sync](): ExampleController[F] = {
    new ExampleController[F]()
  }

}
