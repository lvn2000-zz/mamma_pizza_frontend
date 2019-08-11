import sbt._

object Dependencies {

  val common_library = "com.pizzeria.common" %% "pizzeria-common-library" % "0.1.0"

  object play {
    val playTest = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test"
    //details on https://index.scala-lang.org/leonardehrenfried/play-json-traits/play-json-traits/1.4.4?target=_2.12
    //    val playJsonTrait = "io.leonard" %% "play-json-traits" % "1.4.4"
  }

  object akka {
    val version = "2.5.12"
    val actor = "com.typesafe.akka" %% "akka-actor" % version
    val slf4j = "com.typesafe.akka" %% "akka-slf4j" % version
    val testkit = "com.typesafe.akka" %% "akka-testkit" % version % "test"
    val alpakka_kafka = "com.typesafe.akka" %% "akka-stream-kafka" % "0.21.1"
    val akka_http_version = "10.1.1"
    val http = "com.typesafe.akka" %% "akka-http" % akka_http_version
    val http_test_kit = "com.typesafe.akka" %% "akka-http-testkit" % akka_http_version
  }

  object logging {
    val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  }

  object testPizza {
    val scalatest = "org.scalatest" %% "scalatest" % "3.2.0-SNAP7" % "test"
    val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"
  }

  val typesafeConfig = "com.typesafe" % "config" % "1.3.1"

  object webjar {
    val play = "org.webjars" %% "webjars-play" % "2.6.3"
    val bootstrap = "org.webjars" % "bootstrap" % "4.0.0-2" exclude ("org.webjars", "jquery")
    val jquery = "org.webjars" % "jquery" % "3.3.1"
  }

  val fst = "de.ruedigermoeller" % "fst" % "2.57"
  
  //val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.25"
  object cats {
    val version = "1.2.0"
    val core =  "org.typelevel" %% "cats-core" % version 
  }  
  
}
