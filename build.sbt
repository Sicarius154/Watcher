name := "Watcher"

version := "0.1"

scalaVersion := "2.12.12"

resolvers ++= Seq(
  Classpaths.typesafeReleases,
  "confluent" at "https://packages.confluent.io/maven/",
  Resolver.mavenLocal
)
scalacOptions += "-Ypartial-unification"
lazy val root = (project in file("."))
  .settings(
    organization := "com.chris",
    name := "Watcher",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      Dependencies.Circe.circeGeneric,
      Dependencies.Cats.catsCore,
      Dependencies.Cats.catsEffect,
      Dependencies.Config.pureConfig,
      Dependencies.Logging.logbackClassic,
      Dependencies.Circe.circeParser,
      Dependencies.Testing.scalaTest,
      Dependencies.Finchx.core,
      Dependencies.Finchx.circe,
      Dependencies.Finchx.test
    )
  )

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith "StaticLoggerBinder.class" =>
    MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith "StaticMDCBinder.class" =>
    MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith "StaticMarkerBinder.class" =>
    MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith "io.netty.versions.properties" =>
    MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith "BUILD" => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
