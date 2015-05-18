import sbt._

object Dependencies {
  val spark_version = "1.3.1"

  val baseDeps = Seq (
    "org.apache.spark"    %%  "spark-core"                % spark_version,
    "joda-time"           %   "joda-time"                 % "2.4",
    "org.joda"            %   "joda-convert"              % "1.6",
    "com.google.protobuf" % "protobuf-java"               % "2.4.1",
    "org.spark-project.protobuf" % "protobuf-java"        % "2.4.1-shaded",
    "log4j"               % "log4j"                       % "1.2.17"
  )
}

