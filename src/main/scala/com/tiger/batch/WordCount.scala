package com.tiger.batch

import org.apache.flink.api.scala._

/**
  * @author 王澎
  * @date 2/23/2019
  * @company VRV
  */
object WordCount {
  def main(args: Array[String]) {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val text = env.fromElements(
      "Who's there?",
      "I think I hear them. Stand, ho! Who's there?")

    //    val counts = text.flatMap {
    //      _.toLowerCase.split("\\W+") filter {
    //        _.nonEmpty
    //      }
    //    }
    //      .map {
    //        (_, 1)
    //      }
    //      .groupBy(0)
    //      .sum(1).setParallelism(8)
    val counts = text.flatMap(words => words.split("\\W+").filter(word => word.nonEmpty))
      .map(word => (word, 1)).groupBy("_1").sum("_2")

    counts.print()
  }
}
