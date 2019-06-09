ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cats-examples",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.typelevel" %% "cats-core" % "2.0.0-M1",
      "org.typelevel" %% "cats-macros" % "2.0.0-M1",
      "org.scalaj" %% "scalaj-http" % "2.4.1"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.1"),
    scalacOptions += "-Ypartial-unification"
  )
