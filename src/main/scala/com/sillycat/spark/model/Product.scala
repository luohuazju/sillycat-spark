package com.sillycat.spark.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class Product(){
  var id:Option[Long] = None
  var brand:String = ""
  var productName:String = ""
  var createDate:DateTime = new DateTime(DateTimeZone.forID("UTC"))

  override def toString(): String = {
    return "brand:" + brand + " productName:" + productName + " createDate:" + createDate.toString()
  }

}
