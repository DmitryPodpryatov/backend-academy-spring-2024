package lab2.endpoints

import lab2.repository.AggregateRepository
import lab2.services.InsuranceService
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint

final class InsuranceController[F[_]](service: InsuranceService[F]) extends Controller[F] {

  private val baseUrl: EndpointInput[Unit] = "api" / "v1" / "insurance"
  private val tag: String = "Авто-Страхование"

  override def endpoints: List[ServerEndpoint[Any, F]] = List()
}

object InsuranceController {

  def apply[F[_]](aggregateRepository: AggregateRepository[F]): InsuranceController[F] = {
    val service: InsuranceService[F] = InsuranceService.impl[F](aggregateRepository)

    new InsuranceController[F](service)
  }

}
