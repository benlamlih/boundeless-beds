ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

name             := "BoundlessBeds"
idePackagePrefix := Some("net.benlamlih")

val http4sVersion = "1.0.0-M40"

libraryDependencies ++= Seq(
  "eu.timepit"    %% "refined"             % "0.11.0",
  "io.getquill"   %% "quill-jdbc"          % "4.6.0.1",
  "com.h2database" % "h2"                  % "2.1.214",
  "org.http4s"    %% "http4s-dsl"          % http4sVersion,
  "org.http4s"    %% "http4s-ember-server" % http4sVersion,
  "org.http4s"    %% "http4s-ember-client" % http4sVersion,
  "org.typelevel" %% "log4cats-slf4j"      % "2.5.0",
  "ch.qos.logback" % "logback-classic"     % "1.4.7",
  "com.h2database" % "h2"                  % "2.1.214"
)
