package lab1.endpoints

import cats.Applicative
import lab1.payments.PaymentsService
import lab1.payments.errors.PaymentsError
import lab1.payments.request.TransferByPhoneRequest
import lab1.payments.response.TransferByPhoneResponse
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint.Full

trait PaymentsController[F[_]] {
  def transferByPhone
      : Full[Unit, Unit, TransferByPhoneRequest, PaymentsError, TransferByPhoneResponse, Any, F]
}

object PaymentsController {

  final private class Endpoints[F[_]](service: PaymentsService[F]) extends PaymentsController[F] {
    def transferByPhone
        : Full[Unit, Unit, TransferByPhoneRequest, PaymentsError, TransferByPhoneResponse, Any, F] =
      Protocol.transferByPhone.serverLogic(service.transferByPhone)
  }

  private object Protocol {

    private val baseUrl: EndpointInput[Unit] = "api" / "v1" / "payments" / "transfer" / "by-phone"
    private val tag: String = "Платежи"

    def transferByPhone
        : PublicEndpoint[TransferByPhoneRequest, PaymentsError, TransferByPhoneResponse, Any] =
      endpoint
        .tag(tag)
        .post
        .summary("Перевод по телефону")
        .in(baseUrl)
        .in(jsonBody[TransferByPhoneRequest])
        .errorOut(jsonBody[PaymentsError])
        .out(jsonBody[TransferByPhoneResponse])

  }

  def apply[F[_]: Applicative]: PaymentsController[F] = {
    val service = PaymentsService[F]

    new Endpoints[F](service)
  }

}
