package com.tiger.test.streaming.hbase.phoenix

import java.sql.{Connection, PreparedStatement}
import java.util.UUID

import org.apache.phoenix.jdbc.PhoenixConnectionPool
import org.junit.Test

/**
  * @author 王澎
  */
class HBasePhoenixTests {

  val JDBC_URL = "jdbc:phoenix:hb-bp1qsz6qn45y0vh2o-master1-001.hbase.rds.aliyuncs.com,hb-bp1qsz6qn45y0vh2o-master2-001.hbase.rds.aliyuncs.com,hb-bp1qsz6qn45y0vh2o-master3-001.hbase.rds.aliyuncs.com"

  val PHOENIX_JDBC_DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver"

  @Test
  def phoenixCreateTable(): Unit = {
    var phoenix: PhoenixConnectionPool = null
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      // Phoenix connection pool.
      Class.forName(PHOENIX_JDBC_DRIVER)
      phoenix = new PhoenixConnectionPool(JDBC_URL)
      // Gets connection from phoenix connection pool.
      conn = phoenix.getConnection
      // Creates PreparedStatement.
      ps = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS audit_log.audit_log_2019_05 (id VARCHAR(32) not null PRIMARY KEY)")
      ps.executeUpdate()
      conn.commit()
    } catch {
      case e: Exception => System.out.println(e)
    } finally {
      if (ps != null) ps.close()
      if (conn != null) conn.close()
      if (phoenix != null) phoenix.close()
    }
  }

  @Test
  def phoenixDropTable(): Unit = {
    var phoenix: PhoenixConnectionPool = null
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      // Phoenix connection pool.
      Class.forName(PHOENIX_JDBC_DRIVER)
      phoenix = new PhoenixConnectionPool(JDBC_URL)
      // Gets connection from phoenix connection pool.
      conn = phoenix.getConnection
      // Creates PreparedStatement.
      ps = conn.prepareStatement("DROP TABLE audit_log.audit_log_2019_05")
      val result = ps.executeUpdate()
      println("Execution Result: " + result)
      conn.commit()
    } catch {
      case e: Exception => System.out.println(e)
    } finally {
      if (ps != null) ps.close()
      if (conn != null) conn.close()
      if (phoenix != null) phoenix.close()
    }
  }

  @Test
  def phoenixDeleteTable(): Unit = {
    var phoenix: PhoenixConnectionPool = null
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      // Phoenix connection pool.
      Class.forName(PHOENIX_JDBC_DRIVER)
      phoenix = new PhoenixConnectionPool(JDBC_URL)
      // Gets connection from phoenix connection pool.
      conn = phoenix.getConnection
      // Creates PreparedStatement.
      ps = conn.prepareStatement("DELETE FROM audit_log.audit_log_2019_05")
      val result = ps.executeUpdate()
      println("Execution Result: " + result)
      conn.commit()
    } catch {
      case e: Exception => System.out.println(e)
    } finally {
      if (ps != null) ps.close()
      if (conn != null) conn.close()
      if (phoenix != null) phoenix.close()
    }
  }

  @Test
  def phoenixUpdateOrInsertTable(): Unit = {
    var phoenix: PhoenixConnectionPool = null
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      // Phoenix connection pool.
      Class.forName(PHOENIX_JDBC_DRIVER)
      phoenix = new PhoenixConnectionPool(JDBC_URL)
      // Gets connection from phoenix connection pool.
      conn = phoenix.getConnection
      // Creates PreparedStatement.
      ps = conn.prepareStatement("UPSERT INTO audit_log.audit_log_2019_05(id) values(?)")
      ps.setString(1, UUID.randomUUID().toString.replaceAll("-", ""))
      val result = ps.executeUpdate()
      println("Execution Result: " + result)
      conn.commit()
    } catch {
      case e: Exception => System.out.println(e)
    } finally {
      if (ps != null) ps.close()
      if (conn != null) conn.close()
      if (phoenix != null) phoenix.close()
    }
  }

  @Test
  def phoenixDropSelect(): Unit = {
    var phoenix: PhoenixConnectionPool = null
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      // Phoenix connection pool.
      Class.forName(PHOENIX_JDBC_DRIVER)
      phoenix = new PhoenixConnectionPool(JDBC_URL)
      // Gets connection from phoenix connection pool.
      conn = phoenix.getConnection
      // Creates PreparedStatement.
      ps = conn.prepareStatement("SELECT * FROM audit_log.audit_log_2019_05")
      val resultSet = ps.executeQuery()
      while (resultSet.next()) {
        println(resultSet.getString("id"))
        println("-----------------------------------------------------------")
      }
      conn.commit()
    } catch {
      case e: Exception => System.out.println(e)
    } finally {
      if (ps != null) ps.close()
      if (conn != null) conn.close()
      if (phoenix != null) phoenix.close()
    }
  }


}
