name := "scala-tutorial"

version := "0.1"

val sparkVersion = "2.4.0"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor" % "2.5.19",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "org.scalatest" %% "scalatest" % "3.0.5",
  "joda-time" % "joda-time" % "2.9.3"
)