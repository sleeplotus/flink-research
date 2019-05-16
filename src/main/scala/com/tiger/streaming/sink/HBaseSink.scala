package com.tiger.streaming.sink

import org.apache.flink.configuration.Configuration
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.functions.sink.SinkFunction.Context

/**
  * @author 王澎
  * @date 5/16/2019
  */
class HBaseSink extends RichSinkFunction[ObjectNode] {

  /**
    * Creates hbase client. Initialization method for the function. It is called before the actual working methods.
    *
    * @param parameters The configuration containing the parameters attached to the contract.
    */
  override def open(parameters: Configuration): Unit = {

  }

  /**
    * Writes the given value to the sink. This function is called for every record.
    *
    * @param value   The input record.
    * @param context Additional context about the input record.
    */
  override def invoke(value: ObjectNode, context: Context[_]): Unit = {


  }

  /**
    * Closes hbase client. Tear-down method for the user code. It is called after the last call to the main working methods
    */
  override def close(): Unit = {

  }

}
