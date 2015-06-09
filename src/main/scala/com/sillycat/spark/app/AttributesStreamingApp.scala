package com.sillycat.spark.app

import com.sillycat.spark.base.SparkBaseApp
import com.typesafe.config.ConfigFactory
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
  private val slideDuration = Seconds(3)

  override def getAppName():String = {
    "AttributesStreamingApp"
  }

  override def executeTask(params : Array[String]): Unit ={
    val config = ConfigFactory.load()
    val conf = getSparkConf(config)

    val ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint(getAppName())

    log.info("Prepare the resource.")
    val rdd = generateRdd(ssc)
    processRows(rdd)

    ssc.start()
    ssc.awaitTermination()
  }

  def generateRdd(ssc:StreamingContext) : DStream[String] = {
    val logData = KafkaUtils.createStream(ssc, "localhost:2181", getAppName, Map("test" -> 1)).map(_._2)
    logData
  }

  def processRows(rows: DStream[String]): Unit = {
    val words = rows.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).countByValueAndWindow(windowDuration,slideDuration,2)
    wordCounts.print()
  }

}
