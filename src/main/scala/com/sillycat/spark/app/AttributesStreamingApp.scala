package com.sillycat.spark.app

import com.sillycat.spark.base.SparkBaseApp
import com.typesafe.config.ConfigFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.slf4j.LoggerFactory

/**
 * Created by carl on 5/22/15.
 */
class AttributesStreamingApp extends SparkBaseApp{

  val log = LoggerFactory.getLogger("CountLinesOfKeywordApp")

  private val batchDuration = Seconds(1)
  private val windowDuration = Seconds(30)
  private val slideDuration = Seconds(30)

  override def getAppName():String = {
    "AttributesStreamingApp"
  }

  override def executeTask(params : Array[String]): Unit ={
    val config = ConfigFactory.load()
    val conf = getSparkConf(config)

    val ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint(getAppName())

    log.info("Prepare the resource")
    generateRdd(ssc, "a")

    ssc.start()
    ssc.awaitTermination()
  }

  def generateRdd(ssc:StreamingContext, keyword:String) : Unit = {
    val logData = KafkaUtils.createStream(ssc, "localhost:2181", getAppName, Map("test" -> 1)).map(_._2)
    logData.window(windowDuration, slideDuration).foreachRDD { rdd =>
      val result = processRows(rdd,keyword)
      log.info("Lines with keyword %s : %s".format(keyword, result))
    }
  }

  def processRows(rows: RDD[String], keyword: String):Long = {
    val numA = rows.filter(line => line.contains(keyword)).count()
    numA
  }

}
