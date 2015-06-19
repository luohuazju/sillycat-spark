import sbt._

object Dependencies {
  val spark_version = "1.4.0"
  val hadoop_version = "2.6.0"

  val excludeGuava = ExclusionRule(organization = "com.google.guava")
  val excludeJetty = ExclusionRule(organization = "org.eclipse.jetty")
  val excludeServlet = ExclusionRule(organization = "javax.servlet")
  //https://hadoop.apache.org/docs/r2.6.0/hadoop-project-dist/hadoop-common/dependency-analysis.html
  val baseDeps = Seq (
    "org.apache.spark"    %%  "spark-core"                 % spark_version  excludeAll(excludeGuava),
    "org.apache.spark"    %%  "spark-sql"                  % spark_version  excludeAll(excludeGuava),
    "org.apache.spark"    %%  "spark-streaming"            % spark_version  excludeAll(excludeGuava),
    "org.apache.spark"    %%  "spark-yarn"                 % spark_version  excludeAll(excludeGuava),
    "org.apache.spark"    %%  "spark-streaming-kafka"      % spark_version  excludeAll(excludeGuava),
    "org.apache.spark"    %%  "spark-mllib"                % spark_version  excludeAll(excludeGuava),
    "org.apache.hadoop"   %   "hadoop-client"              % hadoop_version excludeAll(excludeGuava,excludeJetty,excludeServlet),
    "com.google.guava"    %   "guava"                      % "11.0.2",
    "joda-time"           %   "joda-time"                  % "2.4",
    "org.joda"            %   "joda-convert"               % "1.6",
    "com.google.protobuf" %   "protobuf-java"              % "2.4.1",
    "org.spark-project.protobuf" % "protobuf-java"         % "2.4.1-shaded",
    "com.gu"              %%  "prequel"                    % "0.3.12",
    "mysql"               %   "mysql-connector-java"       % "5.1.35"
  )
}

