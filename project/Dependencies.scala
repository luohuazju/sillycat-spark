import sbt._

object Dependencies {
  val spark_version = "1.3.1"
  val hadoop_version = "2.6.0"

  val excludeGuava = ExclusionRule(organization = "com.google.guava")
  val excludeJetty = ExclusionRule(organization = "org.eclipse.jetty")
  val excludeServlet = ExclusionRule(organization = "javax.servlet")
  //https://hadoop.apache.org/docs/r2.6.0/hadoop-project-dist/hadoop-common/dependency-analysis.html
  val baseDeps = Seq (
    "org.apache.spark"    %  "spark-core_2.10"            % spark_version  excludeAll(excludeGuava),
    "org.apache.hadoop"   %  "hadoop-client"               % hadoop_version excludeAll(excludeGuava,excludeJetty,excludeServlet),
    "com.google.guava"    %  "guava"                      % "11.0.2",
    "joda-time"           %  "joda-time"                  % "2.4",
    "org.joda"            %  "joda-convert"               % "1.6",
    "com.google.protobuf" % "protobuf-java"               % "2.4.1",
    "org.spark-project.protobuf" % "protobuf-java"        % "2.4.1-shaded",
    "log4j"               % "log4j"                       % "1.2.17"
  )
}

