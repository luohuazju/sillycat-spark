package com.sillycat.spark

import com.sillycat.spark.base.SparkBaseApp
import org.slf4j.LoggerFactory

import scala.reflect.runtime.universe

/**
 * Created by carl on 5/14/15.
 */
object ExecutorApp extends App{

  val log = LoggerFactory.getLogger("ExecutorApp")

  log.info("Start to Spark tasks!")

  var className = "com.sillycat.spark.app.CountLinesOfKeywordApp"

  className = args.size match  {
    case 1 =>{
      args(0).toString
    }
    case x:Int if (x > 2) => {
      args(0).toString
    }
    case _ => {
      log.error("No args, system will just exit.")
      sys.exit()
    }
  }

  val mirror = universe.runtimeMirror(getClass.getClassLoader);
  val classInstance = Class.forName(className);
  val task = classInstance.newInstance().asInstanceOf[SparkBaseApp];
  task.executeTask(args)

}
