package com.sillycat.spark

import com.sillycat.spark.SparkApp
import org.slf4j.LoggerFactory

import scala.reflect.runtime.universe

/**
 * Created by carl on 5/14/15.
 */
object ExecutorApp extends App{

  val log = LoggerFactory.getLogger("ExecutorApp")

  log.info("Start to Spark tasks!")

  var className = "com.sillycat.spark.app.CountLinesOfKeywordApp"

  args.size match  {
    case 1 =>{
      className = args(0).toString
    }
    case x:Int if (x > 2) => {
      className = args(0).toString
    }
    case _ => {
      log.error("No args, system will just exit.")
      sys.exit()
    }
  }

  val runtimeMirror = universe.runtimeMirror(getClass.getClassLoader)
  val module = runtimeMirror.staticModule(className)
  val obj = runtimeMirror.reflectModule(module)
  val task = obj.asInstanceOf[SparkApp]
  task.executeTask(args)

}
