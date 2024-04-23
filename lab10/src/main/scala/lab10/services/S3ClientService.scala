package lab10.services

import software.amazon.awssdk.services.s3.S3Client

trait S3ClientService[F[_]] {
  def download
}

object S3ClientService {

  final private class Impl[F[_]](client: S3Client) extends S3ClientService[F] {}

  def apply[F[_]](): S3ClientService[F] = {
    val client = ???

    new Impl[F](client)
  }

}
