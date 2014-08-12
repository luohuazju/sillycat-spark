package com.sillycat.spark.app

import org.apache.spark.SparkContext

object LocalSimpleJob extends App{
  val sparkMaster = "spark://192.168.11.12:7077"

  val logFile = "/var/log/apache2" // Should be some file on your system
  val sc = new SparkContext(sparkMaster,
      "Simple Job",
      "/opt/spark",
      List("target/scala-2.10/sillycat-spark-assembly-1.0.jar"),
      Map())
  val logData = sc.textFile(logFile, 2).cache()
  val numAs = logData.filter(line => line.contains("a")).count()
  val numBs = logData.filter(line => line.contains("b")).count()
  println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))

  sc.stop

}

