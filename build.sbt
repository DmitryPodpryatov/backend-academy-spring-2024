import Dependencies._

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "backend-academy-spring-2024"
  )
  .aggregate(
    lab1,
    lab2
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
      case x                   => (assembly / assemblyMergeStrategy).value.apply(x)
    },
    Compile / run / fork := true
  )

lazy val lab2 = (project in file("lab2"))
  .settings(
    name := "lab2",
    libraryDependencies ++= Lab2.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )

lazy val lab3 = (project in file("lab3"))
  .settings(
    name := "lab3",
    libraryDependencies ++= Lab3.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )

lazy val lab4 = (project in file("lab4"))
  .settings(
    name := "lab4",
    libraryDependencies ++= Lab4.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )

lazy val lab5 = (project in file("lab5"))
  .settings(
    name := "lab5",
    libraryDependencies ++= Lab5.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )

lazy val lab6 = (project in file("lab6"))
  .settings(
    name := "lab6",
    libraryDependencies ++= Lab6.dependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-language:higherKinds",
      "-Ymacro-annotations"
    ),
    Compile / run / fork := true
  )
