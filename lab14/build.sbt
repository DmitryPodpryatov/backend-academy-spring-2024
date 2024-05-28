import Dependencies._

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.0.1-SNAPSHOT"

def dockerSettings(name: String) = Seq(
  dockerBaseImage := "openjdk:11",
  Docker / packageName := name,
  Docker / dockerExposedPorts := Seq(8080),
  dockerBuildOptions += "--quiet"
)

lazy val lab14 = (project in file("."))
  .settings(
    name := "lab-14"
  )
  .aggregate(server)

lazy val server = (project in file("server"))
  .settings(
    name := "lab-14-server",
    libraryDependencies ++= Lab14.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )
  .enablePlugins(JavaAppPackaging)
  .settings(dockerSettings("lab-14-server"))
