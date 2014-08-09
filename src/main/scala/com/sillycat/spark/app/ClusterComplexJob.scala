package com.sillycat.spark.app

import com.sillycat.spark.model.Product
import org.apache.spark.{SparkConf, SparkContext}

object ClusterComplexJob extends App {
  val sparkMaster = "local[4]"
  //  val sparkMaster = "spark://localhost:7077"

  val conf = new SparkConf()
  conf.setMaster(sparkMaster)
  conf.setAppName("Complex Job")
  conf.setSparkHome("/opt/spark")
  conf.setJars(List("/Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.jar"))
  conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  conf.set("spark.kryo.registrator", "com.sillycat.spark.model.CustomRegistrator")

  val sc = new SparkContext(conf)

  val item1 = new Product()
  item1.brand = "CK"
  item1.productName = "good"

  val item2 = new Product()
  item2.brand = "apple"
  item2.productName = "good"

  val item3 = new Product()
  item3.brand = "nike"
  item3.productName = "bad"

  val item4 = new Product()
  item4.brand = "cat"
  item4.productName = "bad"


  val products = Seq(item1, item2, item3, item4)

  val name = "good"

  val rdd = sc.makeRDD(products, 2)

  val rdd2 = rdd.groupBy { s => s.productName == name}

  rdd2.collect.foreach { arrays =>
    arrays._2.foreach { item =>
      println("Products are " + arrays._1 + " ===============" + item)
    }
  }

  sc.stop
}