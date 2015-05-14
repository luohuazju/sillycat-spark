package com.sillycat.spark.app.bak

import com.sillycat.spark.model.Product
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by carl on 5/12/15.
 */
object ComplexJob {

    val conf = new SparkConf()
    val sparkMaster = "spark://ubuntu-master1:7077"

    conf.setMaster(sparkMaster)
    conf.setAppName("Complex Job")
    conf.setSparkHome("/opt/spark")
    conf.setJars(List("/Users/carl/work/sillycat/sillycat-spark/target/scala-2.10/sillycat-spark-assembly-1.0.jar"))
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    conf.set("spark.kryo.registrator", "com.sillycat.spark.model.CustomRegistrator")
    //conf.set("spark.driver.host", "ubuntu-master1")
    //conf.set("spark.driver.port", "0")

    val sc = new SparkContext(conf)
    val item1 = new Product()
    item1.brand = "CK"
    item1.productName = "good"

    val item4 = new Product()
    item4.brand = "cat"
    item4.productName = "bad"

    val item2 = new Product()
    item2.brand = "apple"
    item2.productName = "good"

    val item3 = new Product()
    item3.brand = "nike"
    item3.productName = "bad"

    val products = Seq("book1", "book2", "book3", "book4")

    val name = "book1"

    val rdd = sc.makeRDD(products,2)
    val result = rdd.filter{ p =>
      p.equals(name)
    }.count()

    println("================result=" + result)

    sc.stop

}