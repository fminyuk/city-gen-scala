name := "city-gen-scala"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "com.github.scopt" %% "scopt" % "3.7.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.typelevel" %% "cats-core" % "2.0.0-RC1",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)