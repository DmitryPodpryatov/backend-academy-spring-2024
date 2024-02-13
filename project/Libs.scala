import sbt._

object Libs {

  private object V {
    val catsCoreVersion = "2.10.0"
    val catsEffectVersion = "3.5.3"
    val http4sVersion = "0.23.25"
    val tapirVersion = "1.9.9"
    val sttpVersion = "3.9.3"
    val logbackVersion = "1.4.14"
    val circeVersion = "0.14.6"
    val phobosVersion = "0.21.0"
    val scalatestVersion = "3.2.18"
  }

  val cats: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core" % V.catsCoreVersion,
    "org.typelevel" %% "cats-effect" % V.catsEffectVersion
  )

  val http4s: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-ember-client" % V.http4sVersion,
    "org.http4s" %% "http4s-ember-server" % V.http4sVersion,
    "org.http4s" %% "http4s-dsl" % V.http4sVersion,
    "org.http4s" %% "http4s-circe" % V.http4sVersion
  )

  val tapir: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % V.tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % V.tapirVersion
  )

  val sttp: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client3" %% "core" % V.sttpVersion
  )

  val logback: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % V.logbackVersion % Runtime
  )

  val circe: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-generic" % V.circeVersion
  )

  val phobos: Seq[ModuleID] = Seq(
    "ru.tinkoff" %% "phobos-core" % V.phobosVersion
  )

  val scalatest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % V.scalatestVersion
  )

}
