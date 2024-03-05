package lab4.services

import cats.effect.IO
import cats.effect.kernel.Resource
import cats.effect.std.Dispatcher
import cats.effect.unsafe.implicits.global
import lab4.domain.http.{ClientError, ClientResponse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HttpClientServiceSpec extends AnyFlatSpec with Matchers {

  val dispatcherResource: Resource[IO, Dispatcher[IO]] = Dispatcher.sequential[IO]

  "update" should "successfully return from 1" in {
    dispatcherResource.use { implicit dispatcher =>
      for {
        service <- HttpClientService.impl[IO]
        response <- service.update(1)
        _ <- IO.println(response)
        _ = response shouldBe a[Right[_, ClientResponse]]
      } yield ()
    }.unsafeRunSync()
  }

  "update" should "successfully return from 2" in {
    dispatcherResource.use { implicit dispatcher =>
      for {
        service <- HttpClientService.impl[IO]
        response <- service.update(2)
        _ <- IO.println(response)
        _ = response shouldBe a[Right[_, ClientResponse]]
      } yield ()
    }.unsafeRunSync()
  }

  "update" should "successfully return from 3" in {
    dispatcherResource.use { implicit dispatcher =>
      for {
        service <- HttpClientService.impl[IO]
        response <- service.update(3)
        _ <- IO.println(response)
        _ = response shouldBe a[Left[ClientError, _]]
      } yield ()
    }.unsafeRunSync()
  }

  "update" should "successfully return from 4" in {
    dispatcherResource.use { implicit dispatcher =>
      for {
        service <- HttpClientService.impl[IO]
        response <- service.update(4)
        _ <- IO.println(response)
        _ = response shouldBe a[Left[ClientError, _]]
      } yield ()
    }.unsafeRunSync()
  }

}
