package com.tiger.streaming

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author 王澎
  * @date 2/23/2019
  * @company VRV
  */
object SocketWindowWordCount {

  def main(args: Array[String]): Unit = {
    // get the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    // get input data by connecting to the socket
    val text: DataStream[String] = env.socketTextStream("192.168.2.15", 9999, '\n')

    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .flatMap { w => w.split("\\s") }
      .map { w => WordWithCount(w, 1) }
      .keyBy("word")
      .timeWindow(Time.seconds(2))
      .sum("count").setParallelism(2)

    // print the results with a single thread, rather than in parallel
    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")
  }

  /** Data type for words with count */
  case class WordWithCount(word: String, count: Long)

}
