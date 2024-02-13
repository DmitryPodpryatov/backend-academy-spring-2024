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

}
