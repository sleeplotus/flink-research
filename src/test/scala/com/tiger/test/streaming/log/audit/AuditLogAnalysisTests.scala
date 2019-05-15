package com.tiger.test.streaming.log.audit

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.junit.Test

/**
  * @author 王澎
  * @date 5/13/2019
  */
class AuditLogAnalysisTests {

  /**
    * UAT Audit Log Analysis.
    *
    */
  @Test
  def auditLogAnalysis(): Unit = {
    // Gets the execution environment.
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    // Kafka properties
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "172.16.4.182:9092,172.16.4.183:9092,172.16.4.184:9092")
    properties.setProperty("group.id", "t3_group_ops_elk_audit_parse_log")
    // Source
    val kafkaStream = env.addSource(new FlinkKafkaConsumer[String]("topic", new SimpleStringSchema(), properties))

    kafkaStream.print()

    env.execute("Audit Log Streaming Analysis")
  }

}
