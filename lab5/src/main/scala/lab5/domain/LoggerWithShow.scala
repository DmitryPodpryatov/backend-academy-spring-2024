package lab5.domain

import cats.Show.Shown
import org.typelevel.log4cats.Logger

trait LoggerWithShow[F[_]] {
  def error(message: Shown): F[Unit]
  def warn(message: Shown): F[Unit]
  def info(message: Shown): F[Unit]
  def debug(message: Shown): F[Unit]
  def trace(message: Shown): F[Unit]
}

class LoggerWithShowImpl[F[_]](logger: Logger[F]) extends LoggerWithShow[F] {
  override def error(message: Shown): F[Unit] = ???
  override def warn(message: Shown): F[Unit] = ???
  override def info(message: Shown): F[Unit] = ???
  override def debug(message: Shown): F[Unit] = ???
  override def trace(message: Shown): F[Unit] = ???
}

object LoggerWithShow {
  def apply[F[_]](implicit logger: LoggerWithShow[F]): LoggerWithShow[F] = logger
}
