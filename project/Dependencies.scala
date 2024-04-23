import sbt._

trait Dependencies {
  def dependencies: Seq[ModuleID]
}

object Dependencies {

  object Lab1 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.sttp,
      Libs.logback,
      Libs.circe,
      Libs.phobos,
      Libs.scalatest
    ).flatten
  }

  object Lab2 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.sttp,
      Libs.logback,
      Libs.circe,
      Libs.newtype,
      Libs.doobie,
      Libs.flyway,
      Libs.redis4cats,
      Libs.scalatest
    ).flatten
  }

  object Lab3 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.logback,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.scalatest
    ).flatten
  }

  object Lab4 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.sttp,
      Libs.logback,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.doobie,
      Libs.flyway,
      Libs.scalatest
    ).flatten
  }

  object Lab5 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.sttp,
      Libs.logback,
      Libs.log4cats,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.scalatest
    ).flatten
  }

  object Lab6 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.sttp,
      Libs.logback,
      Libs.log4cats,
      Libs.prometheus,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.doobie,
      Libs.flyway,
      Libs.scalatest
    ).flatten
  }

  object Lab7 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.sttp,
      Libs.logback,
      Libs.log4cats,
      Libs.prometheus,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.doobie,
      Libs.flyway,
      Libs.scalatest
    ).flatten
  }

  object Lab8 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.fs2,
      Libs.logback,
      Libs.log4cats,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.scalatest
    ).flatten
  }

  object Lab9 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.fs2,
      Libs.logback,
      Libs.log4cats,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.scalatest,
      Libs.testcontainers
    ).flatten
  }

  object Lab10 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.fs2,
      Libs.s3,
      Libs.logback,
      Libs.log4cats,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.doobie,
      Libs.flyway,
      Libs.scalatest,
      Libs.testcontainers
    ).flatten
  }

}
