import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import sbtassembly.Plugin.AssemblyKeys._


organization := "com.locusenergy.weather"

version := "0.1"

scalaVersion := "2.10.3"

name := "weather-challenge"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

assemblySettings

mainClass in (Compile, run) := Some("com.locusenergy.challenge.Boot")

mainClass in assembly := Some("com.locusenergy.challenge.Boot")


resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaV = "2.2.3"
  val sprayV = "1.2.0"
  Seq(
    "io.spray" % "spray-can" % sprayV,
    "io.spray" % "spray-routing" % sprayV,
    "io.spray" % "spray-testkit" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "org.twitter4j" % "twitter4j-core" % "3.0.5",
    "org.twitter4j" % "twitter4j-stream" % "3.0.5",
    "org.specs2" %% "specs2" % "1.14" % "test",
    "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
    "com.netflix.rxjava" % "rxjava-scala" % "0.16.1",
    "org.mongodb" %% "casbah" % "2.6.3",
    "com.novus" %% "salat" % "1.9.2",
    "io.spray" %%  "spray-json" % "1.2.5",
    "net.databinder.dispatch" %% "dispatch-core" % "0.10.1",
    "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.10.1"
  )
}


