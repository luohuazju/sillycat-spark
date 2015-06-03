sillycat-spark
==============

TODO
2. add unit testing
3. support spark SQL
4. support spark streaming

How to Run the Project

Develop that on Local

>sbt "run com.sillycat.spark.app.CountLinesOfKeywordApp"

>sbt "run com.sillycat.spark.app.CountDeviceSqlApp"


Generate the Binary File
>sbt clean update compile assembly

Run on Binary on Local
>bin/spark-submit --class com.sillycat.spark.ExecutorApp /Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.0.jar com.sillycat.spark.app.CountLinesOfKeywordApp

>bin/spark-submit --class com.sillycat.spark.ExecutorApp /Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.0.jar com.sillycat.spark.app.CountDeviceSqlApp

Run on binary on Remote Spark Cluster
>bin/spark-submit --class com.sillycat.spark.ExecutorApp --master="spark://ubuntu-master:7077" /Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.0.jar com.sillycat.spark.app.CountLinesOfKeywordApp

>bin/spark-submit --class com.sillycat.spark.ExecutorApp --master="spark://ubuntu-master:7077" /Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.0.jar com.sillycat.spark.app.CountDeviceSqlApp

Run on the YARN cluster
>bin/spark-submit --class com.sillycat.spark.ExecutorApp --master yarn-client /Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.0.jar com.sillycat.spark.app.CountLinesOfKeywordApp

>bin/spark-submit --class com.sillycat.spark.ExecutorApp --master yarn-client /Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.0.jar com.sillycat.spark.app.CountDeviceSqlApp
