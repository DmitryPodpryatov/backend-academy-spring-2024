package lab6.services

import cats.FlatMap
import cats.effect.Sync
import cats.tagless.syntax.functorK._
import cats.tagless.{ApplyK, Derive, FunctorK}
import doobie._
import doobie.implicits._
import org.typelevel.log4cats.Logger

trait PostgresClientService[F[_]] {
  def insert(id: String): F[Int]
}

object PostgresClientService {

  final private class Impl extends PostgresClientService[ConnectionIO] {
    override def insert(id: String): ConnectionIO[Int] =
      sql"""
            insert into example(id)
            values ($id)
         """
        .update
        .run
  }

  // https://github.com/tofu-tf/tofu/blob/master/docs/mid.md
  type PreAction[G[_], A] = G[Unit]
  type PostAction[G[_], A] = A => G[Unit]
  type MidAction[G[_], A] = G[A] => G[A]

  final private class Logs[F[_]: FlatMap: Logger] extends PostgresClientService[MidAction[F, *]] {
    override def insert(id: String): F[Int] => F[Int] = ???
  }

  final private class Metrics[F[_]: Sync] extends PostgresClientService[MidAction[F, *]] {
    override def insert(id: String): MidAction[F, Int] = ???
  }

  implicit def functorK: FunctorK[PostgresClientService] = Derive.functorK[PostgresClientService]

  def impl[F[_]: Sync: Logger](xa: Transactor[F]): PostgresClientService[F] = {
    new Impl().mapK(xa.trans)
  }

  private def attach[Alg[_[_]]: ApplyK, F[_]](
      impl: Alg[F],
      mid: Alg[MidAction[F, *]]
  ): Alg[F] = ???

  private def combine[Alg[_[_]]: ApplyK, F[_]](
      left: Alg[MidAction[F, *]],
      right: Alg[MidAction[F, *]]
  ): Alg[MidAction[F, *]] = ???

}
