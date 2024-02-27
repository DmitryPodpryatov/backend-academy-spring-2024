import sbt._

object Libs {

  private object V {
    val pureconfigVersion = "0.17.5"
    val catsCoreVersion = "2.10.0"
    val catsEffectVersion = "3.5.3"
    val http4sVersion = "0.23.25"
    val tapirVersion = "1.9.9"
    val sttpVersion = "3.9.3"
    val logbackVersion = "1.4.14"
    val enumeratumVersion = "1.7.2"
    val circeVersion = "0.14.6"
    val phobosVersion = "0.21.0"
    val newtypeVersion = "0.4.4"
    val doobieVersion = "1.0.0-RC4"
    val flywayVersion = "9.17.0"
    val redis4catsVersion = "1.5.2"
    val scalatestVersion = "3.2.18"
  }

  val pureconfig: Seq[ModuleID] = Seq(
    "com.github.pureconfig" %% "pureconfig" % V.pureconfigVersion
  )

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
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % V.tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-enumeratum" % V.tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % V.tapirVersion
  )

  val sttp: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client3" %% "core" % V.sttpVersion,
    "com.softwaremill.sttp.client3" %% "circe" % V.sttpVersion
  )

  val logback: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % V.logbackVersion
  )

  val enumeratum: Seq[ModuleID] = Seq(
    "com.beachape" %% "enumeratum" % V.enumeratumVersion,
    "com.beachape" %% "enumeratum-circe" % V.enumeratumVersion
  )

  val circe: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-generic" % V.circeVersion
  )

  val phobos: Seq[ModuleID] = Seq(
    "ru.tinkoff" %% "phobos-core" % V.phobosVersion
  )

  val newtype: Seq[ModuleID] = Seq(
    "io.estatico" %% "newtype" % V.newtypeVersion
  )

  val doobie: Seq[ModuleID] = Seq(
    "org.tpolecat" %% "doobie-core" % V.doobieVersion,
    "org.tpolecat" %% "doobie-hikari" % V.doobieVersion,
    "org.tpolecat" %% "doobie-postgres" % V.doobieVersion
  )

  val flyway: Seq[ModuleID] = Seq(
    "org.flywaydb" % "flyway-core" % V.flywayVersion
  )

  val redis4cats: Seq[ModuleID] = Seq(
    "dev.profunktor" %% "redis4cats-effects" % V.redis4catsVersion,
    "dev.profunktor" %% "redis4cats-log4cats" % V.redis4catsVersion
  )

  val scalatest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % V.scalatestVersion
  )

}
