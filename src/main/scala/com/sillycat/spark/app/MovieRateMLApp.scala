package com.sillycat.spark.app

import com.sillycat.spark.base.SparkBaseApp
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkContext
import org.slf4j.LoggerFactory
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating

/**
 * Created by carl on 6/17/15.
 */
class MovieRateMLApp extends SparkBaseApp{

  val log = LoggerFactory.getLogger("MovieRateMLApp")

  override def getAppName():String = {
    "MovieRateMLApp"
  }

  override def executeTask(params : Array[String]): Unit ={
    val config = ConfigFactory.load()
    val conf = getSparkConf(config)
    val sc = new SparkContext(conf)

    val data = sc.textFile("/opt/spark/data/mllib/als/test.data")
    val ratings = data.map(_.split(',') match { case Array(user, item, rate) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })
    val rank = 10
    val numIterations = 20
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    val filePath = "/opt/data/machinelearning-als-testing-4"
    model.save(sc, filePath)
    val sameModel = MatrixFactorizationModel.load(sc, filePath)

    val usersProducts = ratings.map { case Rating(user, product, rate) =>
      (user, product)
    }
    val predictions =
      sameModel.predict(usersProducts).map { case Rating(user, product, rate) =>
        ((user, product), rate)
      }

    predictions.take(5).foreach { case ((user, product), rate) =>
      println(" user=" + user + " product=" + product + " rate=" + rate)
    }



  }

}













