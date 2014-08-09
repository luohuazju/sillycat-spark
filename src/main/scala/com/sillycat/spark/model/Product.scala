package com.sillycat.spark.model

import org.joda.time.DateTime

class Product(){
  var id:Option[Long] = None
  var brand:String = ""
  var productName:String = ""
  var createDate:DateTime = DateTime.now
}
