sillycat-spark
==============

TODO
2. add unit testing
3. support spark SQL
4. support spark streaming

How to Run the Project

Develop that on Local
>sbt "run com.sillycat.spark.app.CountLinesOfKeywordApp"


Generate the Binary File
>sbt clean update compile universal:packageBin

Run on Binary on Local
>bin/sillycat-spark -Dspark.context.master="local[4]" com.sillycat.spark.app.CountLinesOfKeywordApp

Run on binary on Remote Spark Cluster
>bin/sillycat-spark -Dspark.context.master="spark://ubuntu-master:7077" com.sillycat.spark.app.CountLinesOfKeywordApp

Run on the YARN cluster
>bin/sillycat-spark -Dspark.context.master="yarn-cluster" com.sillycat.spark.app.CountLinesOfKeywordApp
