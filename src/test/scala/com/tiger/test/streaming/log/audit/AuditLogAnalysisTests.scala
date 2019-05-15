package com.tiger.test.streaming.log.audit

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerConfig
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
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
      "172.16.4.182:9092,172.16.4.183:9092,172.16.4.184:9092")
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "t3_group_ops_elk_audit_parse_log")
    properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    // Source
    val kafkaStream = env.addSource(
      new FlinkKafkaConsumer[String]("t3_ops_elk_audit_log", new SimpleStringSchema(), properties))

    kafkaStream.print()

    env.execute("Audit Log Streaming Analysis")
  }

}
