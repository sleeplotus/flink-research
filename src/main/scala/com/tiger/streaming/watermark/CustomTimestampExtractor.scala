package com.tiger.streaming.watermark

import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.slf4j.{Logger, LoggerFactory}

/**
 * @author Created by SleepLotus on 2019-08-15
 */
class CustomTimestampExtractor extends BoundedOutOfOrdernessTimestampExtractor[(String, Long)](Time.seconds(0)) {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  override def getCurrentWatermark: Watermark = {
    // this guarantees that the watermark never goes backwards.
    val potentialWM = currentMaxTimestamp - maxOutOfOrderness
    if (potentialWM >= lastEmittedWatermark) lastEmittedWatermark = potentialWM
    logger.info("getCurrentWatermark--currentMaxTimestamp: {}--lastEmittedWatermark: {}", currentMaxTimestamp,
      lastEmittedWatermark)
    new Watermark(lastEmittedWatermark)
  }

  override def extractTimestamp(element: (String, Long), previousElementTimestamp: Long): Long = {
    logger.info("extractTimestamp--element: {}--currentMaxTimestamp: {}--lastEmittedWatermark: {}", element, currentMaxTimestamp,
      lastEmittedWatermark)
    val timestamp = extractTimestamp(element)
    if (timestamp > currentMaxTimestamp) currentMaxTimestamp = timestamp
    timestamp
  }

  override def extractTimestamp(element: (String, Long)): Long = {
    element._2
  }
}
