name := "vertx-core-example"

version := "1.0"

scalaVersion := "2.11.7"

val vertxVersion = "3.1.0"

libraryDependencies ++= Seq(
  "io.vertx" % "vertx-core" % vertxVersion,
  "io.vertx" % "vertx-codegen" % vertxVersion
)
    