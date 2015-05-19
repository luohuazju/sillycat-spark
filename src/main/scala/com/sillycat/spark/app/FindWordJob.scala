package com.sillycat.spark.app

import org.apache.spark.{SparkConf, SparkContext}


object FindWordJob extends App{

    val conf = new SparkConf()
    conf.setAppName("FindWordJob")
//    if(args.size > 2){
//      conf.set("spark.executor.uri",args(1).toString)
//    }
    //conf.setMaster("local[2]")
    //conf.set("spark.executor.uri","local[2]")
    val sc = new SparkContext(conf)
    //val threshold = args(0).toString
    val threshold = "book1"

    val products = Seq("book1", "book2", "book3", "book4")

    val rdd = sc.makeRDD(products,2)
    val result = rdd.filter{ p =>
        p.equals(threshold)
    }.count()

    println("!!!!!!!!!!!!!!================result=" + result)

    sc.stop()

}