scalaVersion := "2.10.0"

name := "Testing SBT"

version := "1.0"

retrieveManaged := true

libraryDependencies ++= Seq(
	"org.scala-tools.time" % "time_2.9.1" % "0.5",
	"org.scalaj" %% "scalaj-http" % "0.3.10"
)