package com.sillycat.spark.app

import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext

/**
 * Created by carl on 5/22/15.
 */
class AttributesStreamingApp extends App{

  private val master = "local[2]"
  private val appName = "example-spark"

  private val batchDuration = Seconds(1)
  private val windowDuration = Seconds(30)
  private val slideDuration = Seconds(3)

  val conf = new SparkConf()
    .setMaster(master)
    .setAppName(appName)

  val ssc = new StreamingContext(conf, batchDuration)

  ssc.start()
  ssc.awaitTermination()

}
