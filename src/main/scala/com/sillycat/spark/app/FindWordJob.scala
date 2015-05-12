package com.sillycat.spark.app

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf


object FindWordJob extends App{

    val conf = new SparkConf()
    conf.setAppName("FindWordJob")
    if(args.size > 2){
      conf.set("spark.executor.uri",args(1).toString)
    }
    val sc = new SparkContext(conf)
    val threshold = args(0).toString

    val products = Seq("book1", "book2", "book3", "book4")

    val rdd = sc.makeRDD(products,2)
    val result = rdd.filter{ p =>
        p.equals(threshold)
    }.count()

    println("================result=" + result)

}