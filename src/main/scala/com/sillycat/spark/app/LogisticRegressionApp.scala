package com.sillycat.spark.app

import com.sillycat.spark.base.SparkBaseApp
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithSGD, SVMWithSGD, SVMModel}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory

/**
 * Created by carl on 6/19/15.
 */
class LogisticRegressionApp extends SparkBaseApp{

  val log = LoggerFactory.getLogger(getAppName())

  override def getAppName():String = {
    "LogisticRegressionApp"
  }

  override def executeTask(params : Array[String]): Unit = {
    val config = ConfigFactory.load()
    val conf = getSparkConf(config)
    val sc = new SparkContext(conf)

    //load all the labeled point
    val data:RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, "/opt/spark/data/mllib/sample_libsvm_data.txt")

    // Split data into training (60%) and test (40%).
    val splits:Array[RDD[LabeledPoint]] = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training:RDD[LabeledPoint] = splits(0).cache()
    val test:RDD[LabeledPoint] = splits(1)

    // Run training algorithm to build the model
    val numIterations = 100
    val model:LogisticRegressionModel = LogisticRegressionWithSGD.train(training, numIterations)

    // Clear the default threshold.
    model.clearThreshold()

    val filePath = "/opt/data/machinelearning-lr-testing-1"
    model.save(sc, filePath)
    val sameModel = LogisticRegressionModel.load(sc, filePath)


    // Compute raw scores on the test set.
    val scoreAndLabels:RDD[(Double,Double)] = test.map { point =>
      val score = sameModel.predict(point.features)
      (score, point.label)
    }

    scoreAndLabels.take(5).foreach { case (score, label) =>
      println("Score = " + score + " Label = " + label);
    }

    // Get evaluation metrics.
    val metrics = new BinaryClassificationMetrics(scoreAndLabels)
    val auROC = metrics.areaUnderROC()

    println("Area under ROC = " + auROC)

  }

}
