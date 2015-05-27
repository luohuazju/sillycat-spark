package com.sillycat.spark.model

import org.joda.time.DateTime

/**
 * Created by carl on 5/25/15.
 */
case class Brand(id: Long,
                 code: String,
                 dateCreated: DateTime,
                 lastUpdated:DateTime,
                 name:String,
                 enabled:Boolean)
