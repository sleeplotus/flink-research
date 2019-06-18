package com.tiger.test.table.functions.timestamps

import java.sql.Date

import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author 王澎
  */
class OrderDateExtractor[T1, T2] extends BoundedOutOfOrdernessTimestampExtractor[(T1, T2, Date)](
  Time.seconds(10)) {
  override def extractTimestamp(element: (T1, T2, Date)): Long = {
    element._3.getTime
  }
}