package com.tiger.test.streaming.log.audit

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.junit.Test

/**
  * @author 王澎
  * @date 5/13/2019
  */
class AuditLogAnalysisTests {

  @Test
  def auditLogAnalysis(): Unit = {
    // Gets the execution environment.
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

  }

}
