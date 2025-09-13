ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core"    % "0.14.9",
  "io.circe" %% "circe-generic" % "0.14.9",
  "io.circe" %% "circe-parser"  % "0.14.9"
)
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"

lazy val root = (project in file("."))
  .settings(
    name := "Projekat"
  )
