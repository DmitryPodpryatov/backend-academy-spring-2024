package lab12.services

object PollingService {

  def run[F[_]](postgresClient: PostgresClientService[F]): F[Unit] = ???

}
