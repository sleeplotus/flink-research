package com.tiger.streaming.watermark

import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author Created by SleepLotus on 2019-08-15
 */
class CustomTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor[(String, Long)](Time.seconds(0)) {
  override def extractTimestamp(element: (String, Long)): Long = {
    element._2
  }
}
