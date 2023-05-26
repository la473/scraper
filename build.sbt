ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "price-scraper"
  )

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % "0.23.14",
  "org.http4s" %% "http4s-dsl" % "0.23.14",
  "org.http4s" %% "http4s-circe" % "0.23.14",
  "io.circe" %% "circe-generic" % "0.14.1",
  "org.typelevel" %% "cats-effect" % "3.4.8",
  "com.softwaremill.macwire" %% "macros" % "2.5.8" % "provided",
  "com.softwaremill.macwire" %% "macrosakka" % "2.5.8" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.5.8",
  "com.softwaremill.macwire" %% "proxy" % "2.5.8",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "com.h2database" % "h2" % "2.1.214",
  "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
  "org.jsoup" % "jsoup" % "1.10.2",
  "co.fs2" %% "fs2-core" % "3.7.0"
)