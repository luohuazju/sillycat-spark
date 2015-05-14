package com.sillycat.spark.app

import com.sillycat.spark.app.bak.FindWordJob
import FindWordJob._
import com.typesafe.config.ConfigFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

object CountLinesOfKeywordApp extends App{

  val log = LoggerFactory.getLogger("CountLinesOfKeywordApp")

  val config = ConfigFactory.load()
  val conf = new SparkConf()
  conf.setMaster(config.getString("spark.context.master"))
  conf.setAppName("SimpleJob")
  conf.setSparkHome(config.getString("spark.context.home"))
  conf.setJars(SparkContext.jarOfClass(this.getClass).toSeq)
  conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

  val sc = new SparkContext(conf)

  var logFile = "file:///" + config.getString("spark.context.home") + "/README.MD"
  var keyword = "a"

  args.size match  {
    case 1 =>{
      keyword = args(0).toString
    }
    case 2 => {
      keyword = args(0).toString
      logFile = args(1).toString
    }
    case _ => {
      log.info("No args!")
    }
  }

  log.info("Prepare the resource from %s".format(logFile))
  val rdd = hadoopRdd(sc, logFile)
  log.info("Executing the calculation based on keyword %s".format(keyword))
  val result = processRows(rdd, keyword)
  log.info("Lines with keyword %s : %s".format(keyword, result))

  sc.stop

  def hadoopRdd(sc:SparkContext, logFile:String) : RDD[String] = {
    val logData = sc.textFile(logFile, 10)
    logData
  }

  def processRows(rows: RDD[String], keyword: String):Long = {
    val numA = rows.filter(line => line.contains(keyword)).count()
    numA
  }
}

