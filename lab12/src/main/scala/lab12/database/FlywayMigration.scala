package lab12.database

import cats.effect.Sync
import cats.syntax.functor._
import lab12.config.PostgresConfig
import org.flywaydb.core.Flyway

object FlywayMigration {

  private def loadFlyway(config: PostgresConfig): Flyway = {
    Flyway.configure()
      .locations("db.migration")
      .cleanDisabled(false)
      .dataSource(config.url, config.user, config.password)
      .load()
  }

  def migrate[F[_]](config: PostgresConfig)(implicit F: Sync[F]): F[Unit] = {
    F.delay(loadFlyway(config).migrate()).void
  }

  def clear[F[_]](config: PostgresConfig)(implicit F: Sync[F]): F[Unit] = {
    F.delay(loadFlyway(config).clean()).void
  }

}
