package lab1.payments

import cats.Applicative
import cats.syntax.applicative._
import cats.syntax.either._
import lab1.payments.errors.PaymentsError
import lab1.payments.request.TransferByPhoneRequest
import lab1.payments.response.TransferByPhoneResponse

trait PaymentsService[F[_]] {
  def transferByPhone(
      request: TransferByPhoneRequest
  ): F[Either[PaymentsError, TransferByPhoneResponse]]
}

object PaymentsService {

  final private class Impl[F[_]: Applicative] extends PaymentsService[F] {
    override def transferByPhone(
        request: TransferByPhoneRequest
    ): F[Either[PaymentsError, TransferByPhoneResponse]] =
      TransferByPhoneResponse().asRight[PaymentsError].pure[F]
  }

  def apply[F[_]: Applicative]: PaymentsService[F] = new Impl[F]
}
