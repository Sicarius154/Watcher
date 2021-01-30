import sbt._

object Dependencies {

  object Cats {
    val catsVersion = "2.0.0"
    val catsCore = "org.typelevel" %% "cats-core" % catsVersion
    val catsEffect = "org.typelevel" %% "cats-effect" % catsVersion
  }

  object Config {
    val pureConfigVersion = "0.12.3"
    val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
  }

  object JWT {
    val jwtScalaVersion = "4.2.0"
    val jwtCirce = "com.pauldijou" %% "jwt-circe" % jwtScalaVersion
  }

  object Testing {
    val scalaTestVersion = "3.1.2"
    val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  }

  object Logging {
    val logbackVersion = "1.2.3"
    val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion
  }

  object Circe {
    val circeVersion = "0.13.0"
    val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
    val circeParser = "io.circe" %% "circe-parser" % circeVersion
  }

  object Finchx {
    val finchVersion = "0.32.1"
    val core = "com.github.finagle" %% "finchx-core" % finchVersion
    val circe = "com.github.finagle" %% "finchx-circe" % finchVersion
    val test = "com.github.finagle" %% "finchx-test" % finchVersion
  }

  object Doobie {
    val doobieVersion = "0.9.0"
    val core = "org.tpolecat" %% "doobie-core" % doobieVersion
    val postgres = "org.tpolecat" %% "doobie-postgres" % doobieVersion
    val h2 = "org.tpolecat" %% "doobie-h2" % doobieVersion
    val hikari = "org.tpolecat" %% "doobie-hikari" % doobieVersion
    val specs2 = "org.tpolecat" %% "doobie-specs2" % doobieVersion
  }

}
