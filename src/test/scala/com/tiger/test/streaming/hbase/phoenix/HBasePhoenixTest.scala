package com.tiger.test.streaming.hbase.phoenix

import java.sql.{Connection, PreparedStatement}

import org.apache.phoenix.jdbc.PhoenixConnectionPool
import org.junit.Test

/**
  * @author 王澎
  */
class HBasePhoenixTest {

  @Test
  def phoenix(): Unit = {
    try {
      Class.forName("org.apache.phoenix.jdbc.PhoenixDriver")
      val phoenix: PhoenixConnectionPool = new PhoenixConnectionPool(
        "jdbc:phoenix:hb-bp1qsz6qn45y0vh2o-master1-001.hbase.rds.aliyuncs.com,hb-bp1qsz6qn45y0vh2o-master2-001.hbase.rds.aliyuncs.com,hb-bp1qsz6qn45y0vh2o-master3-001.hbase.rds.aliyuncs.com")

      val conn: Connection = phoenix.getConnection
      val ps: PreparedStatement = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS audit_log_2019_05 (id VARCHAR(255) CONSTRAINT audit_pk PRIMARY KEY )")
      ps.execute()
      conn.commit()

      if (ps != null) ps.close()
      if (conn != null) conn.close()
    } catch {
      case e: Exception => System.out.println(e)
    }

  }


}
