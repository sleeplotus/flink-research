package com.tiger.streaming.watermark

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author 王澎
 */
class SocketWatermarkWindowCount {

  def main(args: Array[String]): Unit = {
    socketWatermarkWindowCount()
  }

  def socketWatermarkWindowCount(): Unit = {
    // get the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(100)

    // get input data by connecting to the socket
    val text: DataStream[String] = env.socketTextStream("localhost", 9999, '\n')

    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .map { element => {
        val elements = element.split(",")
        val result = elements.length match {
          case len: Int if len >= 2 => (elements(0), elements(1).toLong)
          case len: Int if len == 1 => (elements(0), 0L)
          case _ => ("Default", 0L)
        }
        result
      }
      }.assignTimestampsAndWatermarks(new CustomTimestampExtractor)
      .keyBy(0)
      .timeWindow(Time.seconds(2))
      .sum(1)

    // print the results with a single thread, rather than in parallel
    windowCounts.print().setParallelism(1)

    env.execute("Socket Watermark")
  }

}
