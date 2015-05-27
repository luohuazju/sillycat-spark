package com.sillycat.spark.app

import com.sillycat.spark.base.MysqlDBHelper
import com.sillycat.spark.dao.DeviceDAO._
import com.sillycat.spark.dao.{BrandDAO, DeviceDAO}
import com.sillycat.spark.model.Device
import net.noerd.prequel.DatabaseConfig
import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

import scala.math._

/**
 * Created by carl on 5/22/15.
 */
object CountDeviceSqlApp extends App{

  val log = LoggerFactory.getLogger("CountDeviceSqlApp")

  private val master = "local[2]"
  private val appName = "CountDeviceSqlApp"

  val conf = new SparkConf()
    .setMaster(master)
    .setAppName(appName)

  val sc = new SparkContext(conf)
  val sqlc = new SQLContext(sc)

  val deviceDAO = new DeviceDAO(sqlc)
  val brandDAO = new BrandDAO(sqlc)

  import sqlc.implicits._

  val devices = deviceDAO.getDeviceRdd(sc).toDF()
  devices.registerTempTable("devices")

  log.info("select brandId, count(*) from devices where sendable = 1 group by brandId")
  deviceDAO.numberOfDevices().collect().foreach(println)

  sc.stop();

}
