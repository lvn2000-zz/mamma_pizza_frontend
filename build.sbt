import Resolvers._
import Dependencies._

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .settings(
    buildSettings,
    resolvers ++= myResolvers,
    libraryDependencies ++= (commonDeps ++ testDeps)
  )

// factor out common settings into a sequence
lazy val buildSettings = Seq(
  name := "Pizzeria Frontend",
  organization := "com.pizzeria.frontend",
  version := "0.1.0",
  scalaVersion:= "2.12.8",
  fork in Test := true,
  fork in run := true
  //mainClass in(Compile) := Some("com.pizzeria.frontend.main")
)

// Sub-project specific dependencies
lazy val commonDeps = Seq(
//  play.h2d,
//  play.playJsonTrait,
  common_library,
  akka.actor, akka.slf4j, akka.http, akka.alpakka_kafka,
  logging.logback, typesafeConfig, fst, cats.core
)

unmanagedBase := baseDirectory.value / "extlibs" 

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += ehcache

lazy val testDeps = Seq(play.playTest, testPizza.scalatest, testPizza.scalacheck, akka.testkit, akka.http_test_kit)

//unmanagedResourceDirectories in Test +=  baseDirectory ( _ /"target/web/public/test" )



