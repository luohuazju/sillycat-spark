import sbt.Keys._
import sbt._
import Resolvers._
import Dependencies._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyKeys._
import sbtassembly.MergeStrategy

object Build extends Build {
  val projectName = "sillycat-spark"

  val buildSettings = Seq(
    name := projectName,
    organization := "com.sillycat",
    version := "1.0.0",
    scalaVersion := "2.10.5",
    crossScalaVersions := Seq("2.10.5", "2.11.6")
  )

  lazy val root = Project(id = projectName, base = file("."))
    .configs(IntegrationTest)
    .settings(Defaults.itSettings: _*)
    .settings(
      buildSettings,
      resolvers ++= myResolvers,
      libraryDependencies ++= baseDeps,
      assemblyMergeStrategy in assembly := mergeFirst,
      mainClass in assembly := Some("com.jobs2careers.ExecutorApp"),
      //credentials += Credentials("sillycat_repo", "ubuntu-pilot", "developer", "developer"),
      //credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
      ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
      isSnapshot := true,
      publishTo := Some("Sillycat Realm" at "http://ubuntu-pilot:8080/artifactory/libs-release-local")

    )

  val metaRegex = """META.INF(.)*""".r

  lazy val mergeFirst: String => MergeStrategy = {
    case metaRegex(_) => MergeStrategy.discard
    case _ => MergeStrategy.first
  }
}













