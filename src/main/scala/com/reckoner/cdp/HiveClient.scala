package com.reckoner.cdp

import java.sql.{Connection, DriverManager, SQLException}

import org.apache.logging.log4j.scala.Logging
import org.joda.time.DateTime

class HiveClient(host: String, port: String, database: String, user: String, password: String) extends Logging {
    val driverName = "org.apache.hive.jdbc.HiveDriver"
    var conn:Connection = _;
    def createConn(): Unit = {
        Class.forName(driverName)
        logger.info(
            s"Connect to ===> jdbc:hive2://${host}:${port}/${database}"
        )
        conn = DriverManager.getConnection(
            s"jdbc:hive2://${host}:${port}/${database}",
            user,
            password
        )
    }

    def exec(sql: String): Unit = {
        logger.info(sql)
        val stmt = conn.createStatement()
        stmt.execute(sql)
    }
}
