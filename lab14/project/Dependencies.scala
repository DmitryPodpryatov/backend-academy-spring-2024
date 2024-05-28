import sbt._

trait Dependencies {
  def dependencies: Seq[ModuleID]
}

object Dependencies {

  object Lab14 extends Dependencies {
    override def dependencies: Seq[ModuleID] = Seq(
      Libs.pureconfig,
      Libs.cats,
      Libs.http4s,
      Libs.tapir,
      Libs.logback,
      Libs.log4cats,
      Libs.enumeratum,
      Libs.circe,
      Libs.newtype,
      Libs.scalatest
    ).flatten
  }

}
