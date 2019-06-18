package com.tiger.test.logback

import org.junit.Test
import org.slf4j.{Logger, LoggerFactory}

/**
  * @author 王澎
  */
class LogbackTests {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  @Test def log(): Unit = {
    logger.trace("A TRACE {}", "Message")
    logger.debug("A DEBUG {}", "Message")
    logger.info("{} {} {}", "An", "INFO", "Message")
    logger.warn("A WARN Message")
    logger.error("An ERROR Message")
  }


}
