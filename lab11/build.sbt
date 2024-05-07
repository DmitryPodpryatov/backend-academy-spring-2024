import Dependencies._

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.0.1-SNAPSHOT"

lazy val lab11 = (project in file("."))
  .settings(
    name := "lab-11"
  )
  .aggregate(server, producer, consumer)

lazy val server = (project in file("server"))
  .settings(
    name := "lab-11-server",
    libraryDependencies ++= Lab11.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )

lazy val producer = (project in file("producer"))
  .settings(
    name := "lab-11-producer",
    libraryDependencies ++= Lab11.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )

lazy val consumer = (project in file("consumer"))
  .settings(
    name := "lab-11-consumer",
    libraryDependencies ++= Lab11.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )
