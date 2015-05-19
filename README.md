sillycat-spark
==============

TODO

1. manage the project in Build.scala, not build.sbt
2. add unit testing
3. support spark SQL
4. support spark streaming

1. Run that on Local
>sbt "run com.sillycat.spark.app.CountLinesOfKeywordApp"

2. Run on Binary on Local
>bin/sillycat-spark -Dspark.context.master="local[4]" com.sillycat.spark.app.CountLinesOfKeywordApp

3. Run on binary on Remote Spark Cluster
>bin/sillycat-spark -Dspark.context.master="spark://ubuntu-master:7077" com.sillycat.spark.app.CountLinesOfKeywordApp

Compile
>sbt clean update compile universal:packageBin