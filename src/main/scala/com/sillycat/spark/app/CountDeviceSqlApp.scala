package com.sillycat.spark.app

import com.sillycat.spark.base.{SparkBaseApp, MysqlDBHelper}
import com.sillycat.spark.dao.DeviceDAO._
import com.sillycat.spark.dao.{BrandDAO, DeviceDAO}
import com.sillycat.spark.model.Device
import com.typesafe.config.ConfigFactory
import net.noerd.prequel.DatabaseConfig
import org.apache.spark.rdd.{RDD, JdbcRDD}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

import scala.math._

/**
 * Created by carl on 5/22/15.
 */
class CountDeviceSqlApp extends SparkBaseApp{

  val log = LoggerFactory.getLogger("CountDeviceSqlApp")

  override def getAppName():String = {
    "CountDeviceSqlApp"
  }

  override def executeTask(params : Array[String]): Unit ={
    val config = ConfigFactory.load()
    val conf = getSparkConf(config)
    val sc = new SparkContext(conf)
    val sqlc = new SQLContext(sc)

    val deviceDAO = new DeviceDAO(sqlc)
    val brandDAO = new BrandDAO(sqlc)

    import sqlc.implicits._

    val devices = deviceDAO.getDeviceRdd(sc).toDF()
    devices.registerTempTable("devices")

    log.info("select brandId, count(*) from devices where sendable = 1 group by brandId")
    deviceDAO.numberOfDevices().collect().foreach(println)

    sc.stop
  }

  def hadoopRdd(sc:SparkContext, logFile:String) : RDD[String] = {
    val logData = sc.textFile(logFile, 10)
    logData
  }

  def processRows(rows: RDD[String], keyword: String):Long = {
    val numA = rows.filter(line => line.contains(keyword)).count()
    numA
  }

}
