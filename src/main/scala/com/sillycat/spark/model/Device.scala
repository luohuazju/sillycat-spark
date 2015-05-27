package com.sillycat.spark.model

import java.sql.Timestamp

/**
 * Created by carl on 5/25/15.
 */
case class Device(id:Long,
                  brandId:Long,
                  dateCreated:Timestamp,
                  lastUpdated:Timestamp,
                  deviceId:String,
                  osType:String,
                  osVersion:String,
                  searchRadius:Int,
                  sdkMajorVersion:Int,
                  lastTimeZone:String,
                  sendable:Boolean
                  )
