import sbt._

trait Dependencies {
  def dependencies: Seq[ModuleID]
}

object Dependencies {

  object Lab11 extends Dependencies {
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
