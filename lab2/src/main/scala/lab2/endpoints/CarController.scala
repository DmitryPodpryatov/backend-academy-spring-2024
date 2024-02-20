package lab2.endpoints

import lab2.domain.car.{Car, CarId}
import lab2.domain.car.error.CarError
import lab2.repository.CarRepository
import lab2.services.CarService
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint

final class CarController[F[_]](service: CarService[F]) extends Controller[F] {
  private val baseUrl: EndpointInput[Unit] = "api" / "v1" / "car"
  private val tag: String = "Авто"

  val create: Endpoint[Unit, Car, CarError, CarId, Any] =
    endpoint
      .tag(tag)
      .post
      .in(baseUrl)
      .in(jsonBody[Car])
      .errorOut(jsonBody[CarError])
      .out(jsonBody[CarId])

  val find: Endpoint[Unit, CarId, CarError, Option[Car], Any] =
    endpoint
      .tag(tag)
      .get
      .in(baseUrl)
      .in(query[CarId]("id"))
      .errorOut(jsonBody[CarError])
      .out(jsonBody[Option[Car]])

  val delete: Endpoint[Unit, CarId, CarError, Car, Any] =
    endpoint
      .tag(tag)
      .delete
      .in(baseUrl)
      .in(query[CarId]("id"))
      .errorOut(jsonBody[CarError])
      .out(jsonBody[Car])

  override def endpoints: List[ServerEndpoint[Any, F]] = List(
    create.serverLogic(service.create),
    find.serverLogic(service.find),
    delete.serverLogic(service.delete)
  )
}

object CarController {

  def apply[F[_]](carRepository: CarRepository[F]): CarController[F] = {
    val service: CarService[F] = CarService.impl[F](carRepository)

    new CarController[F](service)
  }

}
