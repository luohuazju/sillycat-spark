package com.sillycat.spark.dao

import java.sql.{Timestamp, ResultSet}

import com.sillycat.spark.base.MysqlDBHelper
import com.sillycat.spark.model.{Device}
import net.noerd.prequel.DatabaseConfig
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{RDD, JdbcRDD}
import org.apache.spark.sql.{Row, SQLContext}
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

import scala.math._

/**
 * Created by carl on 5/25/15.
 */
class DeviceDAO (sqlc: SQLContext) {

  val log = LoggerFactory.getLogger("CountDeviceSqlApp")

  val lookbackDays = MysqlDBHelper.config.getInt("lookback.days")

  //spark SQL parts
  def numberOfDevices(): RDD[(Long, Long)] = {
    sqlc.sql("select brandId, count(*) from devices where sendable = 1 group by brandId").map(row => (row.getLong(0), row.getLong(1)))
  }


  //mysql parts
  def minEvent(db: DatabaseConfig, startDate: DateTime): Long = {
    import net.noerd.prequel.SQLFormatterImplicits._
    val sd = startDate.minusDays(lookbackDays).withTimeAtStartOfDay
    val ed = sd.plusDays(1)

    db.transaction { tx =>
      tx.selectLong("select coalesce(min(d.id), 0) from device d where d.last_updated >= ? and d.last_updated < ?", sd, ed)
    }
  }

  def maxEvent(db: DatabaseConfig): Long = {
    db.transaction { tx =>
      tx.selectLong("""select coalesce(max(d.id), 0) from device d""")
    }
  }

  def getDeviceRdd(sc: SparkContext): RDD[Device] = {
    val minId = minEvent(MysqlDBHelper.prequelDatabase, DateTime.now)
    val maxId = maxEvent(MysqlDBHelper.prequelDatabase)
    val numPartitions = max(((maxId - minId) / MysqlDBHelper.config.getInt("devicesPerPartition")).toInt, 8)

    log.info("minId = " + minId + " maxId = " + maxId + " numPartitions = " + numPartitions)

    val jrdd = new JdbcRDD(sc,
      MysqlDBHelper.nativeConnection,
      """select id, tenant_id, date_created, last_updated, device_id, os_type, os_version,
          search_radius, sdk_major_version, last_time_zone, sendable
         from
          device d
         where
          ? <= d.id and
          d.id <= ? """,
      minId, maxId, numPartitions, DeviceDAO.dbToDevice _)
    jrdd
  }

}

object DeviceDAO {
  def sparkToDevice(row: Row): Device = Device(
    row.getLong(0),
    row.getLong(1),
    new Timestamp(row.getDate(2).getTime),
    new Timestamp(row.getDate(3).getTime),
    row.getString(4),
    row.getString(5),
    row.getString(6),
    row.getInt(7),
    row.getInt(8),
    row.getString(9),
    row.getBoolean(10))

  def dbToDevice(rs: ResultSet):Device = Device(
    rs.getLong(1),
    rs.getLong(2),
    new Timestamp(rs.getDate(3).getTime),
    new Timestamp(rs.getDate(4).getTime),
    rs.getString(5),
    rs.getString(6),
    rs.getString(7),
    rs.getInt(8),
    rs.getInt(9),
    rs.getString(10),
    rs.getBoolean(11)
  )
}
