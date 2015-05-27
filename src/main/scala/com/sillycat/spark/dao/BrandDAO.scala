package com.sillycat.spark.dao

import com.sillycat.spark.model.Brand
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.joda.time.DateTime

/**
 * Created by carl on 5/25/15.
 */
class BrandDAO (sqlc: SQLContext) {

  import com.sillycat.spark.dao.BrandDAO._

  def numberOfValidDevices(): RDD[(Long, Long)] =
    sqlc.sql("SELECT brandId, COUNT(*) FROM devices where sendable = 1 GROUP BY brandId").map(row => (row.getLong(0), row.getLong(1)))

}

object BrandDAO {
  private def toBrand(row: Row): Brand = Brand(row.getLong(0),
                                                row.getString(1),
                                                new DateTime(row.getDate(2)),
                                                new DateTime(row.getDate(3)),
                                                row.getString(4),
                                                row.getBoolean(5))
}