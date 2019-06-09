import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cats-examples",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0-M1",
    libraryDependencies += "org.typelevel" %% "cats-macros" % "2.0.0-M1",
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.1"),
    scalacOptions += "-Ypartial-unification"
  )
