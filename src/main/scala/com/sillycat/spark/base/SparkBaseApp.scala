package com.sillycat.spark.base

import com.typesafe.config.Config
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by carl on 5/18/15.
 */
trait SparkBaseApp {

  def executeTask(params : Array[String]): Unit ={

  }

  def getAppName():String = {
    "defaultJob"
  }

  def getSparkConf(config: Config):SparkConf = {
    val conf = new SparkConf()
    //conf.setMaster(config.getString("spark.context.master"))
    conf.setAppName(getAppName)
    conf.setSparkHome(config.getString("spark.context.home"))
    conf.setJars(SparkContext.jarOfClass(this.getClass).toSeq)
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    conf
  }

}
