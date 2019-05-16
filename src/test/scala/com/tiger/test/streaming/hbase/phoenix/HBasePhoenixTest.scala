package com.tiger.test.streaming.hbase.phoenix

import java.sql.{Connection, PreparedStatement}

import org.apache.phoenix.jdbc.PhoenixConnectionPool
import org.junit.Test

/**
  * @author 王澎
  */
class HBasePhoenixTest {

  val JDBC_URL = "jdbc:phoenix:hb-bp1qsz6qn45y0vh2o-master1-001.hbase.rds.aliyuncs.com,hb-bp1qsz6qn45y0vh2o-master2-001.hbase.rds.aliyuncs.com,hb-bp1qsz6qn45y0vh2o-master3-001.hbase.rds.aliyuncs.com"

  @Test
  def phoenixCreateTable(): Unit = {
    var phoenix: PhoenixConnectionPool = null
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      // Phoenix connection pool.
      Class.forName("org.apache.phoenix.jdbc.PhoenixDriver")
      phoenix = new PhoenixConnectionPool(JDBC_URL)
      // Gets connection from phoenix connection pool.
      conn = phoenix.getConnection
      // Creates PreparedStatement.
      ps = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS audit_log_2019_05 (id VARCHAR(255) not null PRIMARY KEY)")
      ps.execute()
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
  def phoenixSelectTable(): Unit = {


  }


}
