import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

name := "sillycat-spark" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8") 

resolvers ++= Seq(
 Resolver.defaultLocal,
 "MAVEN repo"        at "http://repo1.maven.org/maven2",
 "sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
 "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
 "typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
 "spray repo"         at "http://repo.spray.io/",
 "Spray repo second"  at "http://repo.spray.cc/",
 "Akka repo"          at "http://repo.akka.io/releases/"
)

libraryDependencies ++= Seq(
    "org.apache.spark"    %%  "spark-core"                % "0.9.0-incubating",
    "joda-time"           %   "joda-time"                 % "2.4",
    "org.joda"            %   "joda-convert"              % "1.6",
  "com.google.protobuf" % "protobuf-java" % "2.4.1",
  "org.spark-project.protobuf" % "protobuf-java" % "2.4.1-shaded"
)

seq(Revolver.settings: _*)

seq(assemblySettings: _*)

mainClass in assembly := Some("com.sillycat.spark.app.ExecutorApp")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "jasper", xs @ _*) => MergeStrategy.first
    case PathList("org", "fusesource", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "commons", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "commons", "beanutils", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "commons", "collections", xs @ _*) => MergeStrategy.first
    case PathList("com", "esotericsoftware", "minlog", xs @ _*) => MergeStrategy.first
    case PathList("org", "eclipse", xs @ _*) => MergeStrategy.first
    case PathList("META-INF", xs @ _*) =>
      (xs map {_.toLowerCase}) match {
            case ("manifest.mf" :: Nil) | ("eclipsef.rsa" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
              MergeStrategy.discard
            case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") || ps.last.endsWith("pom.properties") || ps.last.endsWith("pom.xml") =>
              MergeStrategy.discard
            case "plexus" :: xs =>
              MergeStrategy.discard
            case "services" :: xs =>
              MergeStrategy.filterDistinctLines
            case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
              MergeStrategy.filterDistinctLines
            case ps @ (x :: xs) if ps.last.endsWith(".jnilib") || ps.last.endsWith(".dll") =>
              MergeStrategy.first
            case ps @ (x :: xs) if ps.last.endsWith(".txt") =>
              MergeStrategy.discard
            case ("notice" :: Nil) | ("license" :: Nil) | ("mailcap" :: Nil )=>
              MergeStrategy.discard
            case _ => MergeStrategy.deduplicate
      }
    case "application.conf" => MergeStrategy.concat
    case "about.html" => MergeStrategy.discard
    case "plugin.properties" => MergeStrategy.first
    case x => old(x)
  }
}

artifact in (Compile, assembly) ~= { art =>
  art.copy(`classifier` = Some("assembly"))
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter {_.data.getName == "parboiled-scala_2.10.0-RC1-1.1.3.jar"}
}

addArtifact(artifact in (Compile, assembly), assembly)




