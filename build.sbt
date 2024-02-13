import Dependencies.Lab1

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.0.1-SNAPSHOT"

Compile / run / fork := true

lazy val root = (project in file("."))
  .settings(
    name := "backend-academy-spring-2024"
  )
  .aggregate(
    lab1
  )

lazy val lab1 = (project in file("lab1"))
  .settings(
    name := "lab1",
    libraryDependencies ++= Lab1.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    assembly / mainClass := Some("lab1.Main"),
    assembly / assemblyJarName := "lab1.jar",
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )
