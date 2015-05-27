package com.sillycat.spark.base

import java.sql.DriverManager

import com.typesafe.config.ConfigFactory
import net.noerd.prequel.DatabaseConfig
import net.noerd.prequel.IsolationLevels
import net.noerd.prequel.SQLFormatter

/**
 * Created by carl on 5/26/15.
 */
object MysqlDBHelper extends Serializable {

  val config = ConfigFactory.load()

  /** Prequel mysql driver */
  val prequelDatabase = DatabaseConfig(
    driver = config.getString("mysql.driver"),
    jdbcURL = config.getString("mysql.url"),
    username = config.getString("mysql.user"),
    password = config.getString("mysql.password"),
    sqlFormatter = SQLFormatter.HSQLDBSQLFormatter,
    isolationLevel = IsolationLevels.RepeatableRead
  )

  /** native mysql driver */
  val nativeConnection = () => {
    Class.forName(config.getString("mysql.driver"))
    DriverManager.getConnection(
      config.getString("mysql.url"),
      config.getString("mysql.user"),
      config.getString("mysql.password"))
  }

}
