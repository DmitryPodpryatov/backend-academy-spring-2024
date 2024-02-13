package lab1.integrations

trait TransferClient[F[_]] {}

object TransferClient {

  final private class Impl[F[_]] extends TransferClient[F] {}

  def apply[F[_]]: TransferClient[F] = new Impl[F]
}
