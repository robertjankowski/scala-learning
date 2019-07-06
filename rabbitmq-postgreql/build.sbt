name := "rabbitmq-postgreql"

version := "0.1"

scalaVersion := "2.12.8"

val opRabbitVersion = "2.1.0"

libraryDependencies ++= Seq(
  "com.spingo" %% "op-rabbit-core" % opRabbitVersion,
  "com.spingo" %% "op-rabbit-play-json" % opRabbitVersion,
  "com.spingo" %% "op-rabbit-json4s" % opRabbitVersion,
  "com.spingo" %% "op-rabbit-airbrake" % opRabbitVersion,
  "com.spingo" %% "op-rabbit-akka-stream" % opRabbitVersion,
  "com.typesafe.slick" %% "slick" % "3.3.1",
  "org.slf4j" % "slf4j-nop" % "1.7.10",
  "com.h2database" % "h2" % "1.4.187",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

fork in run := true
