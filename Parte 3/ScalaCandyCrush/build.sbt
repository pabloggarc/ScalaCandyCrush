ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Graficos"
  )
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
