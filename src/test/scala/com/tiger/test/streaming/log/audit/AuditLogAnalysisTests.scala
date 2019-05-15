package com.tiger.test.streaming.log.audit

import java.util.Properties

import org.apache.flink.core.fs.FileSystem
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema
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
    env.enableCheckpointing(600000)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // Kafka properties
    val properties = new Properties()
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
      "172.16.4.182:9092,172.16.4.183:9092,172.16.4.184:9092")
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "t3_group_ops_elk_audit_parse_log")
    properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    // Source
    val kafkaStream = env.addSource(
      new FlinkKafkaConsumer[ObjectNode]("t3_ops_elk_audit_log",
        new JSONKeyValueDeserializationSchema(true),
        properties))

    val result = kafkaStream.map(line => line.get("request_parameters"))
    // Sink
    result.writeAsText("out/log/audit/streaming/parameters", FileSystem.WriteMode.OVERWRITE).setParallelism(1)
    result.print().setParallelism(1)
    // Execution
    env.execute("Audit Log Streaming Analysis")
  }

}
