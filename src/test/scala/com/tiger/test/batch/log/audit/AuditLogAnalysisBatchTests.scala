package com.tiger.test.batch.log.audit

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem
import org.junit.Test

/**
  * @author 王澎
  * @date 5/13/2019
  */
class AuditLogAnalysisBatchTests {


  @Test
  def analyzeApi(): Unit = {
    // Gets the execution environment.
    val env = ExecutionEnvironment.getExecutionEnvironment
    // Source
    val csvInput = env.readCsvFile[Line](
      "file:///MyRepository/T3出行/ELK/log analysis/audit log",
      ignoreFirstLine = true, includedFields = Array(1))
    // Transformation
    val result = csvInput.filter(line => line.value.nonEmpty).distinct().sortPartition(0,
      Order.ASCENDING).map(line => line.value)
    // Sink
    result.writeAsText("out/log/audit/batch/api", FileSystem.WriteMode.OVERWRITE).setParallelism(1)
    result.print()
  }

  @Test
  def analyzeRequestParameters(): Unit = {
    // Gets the execution environment.
    val env = ExecutionEnvironment.getExecutionEnvironment
    // Source
    val csvInput = env.readTextFile(
      "file:///MyRepository/T3出行/ELK/log analysis/audit log")
    // Transformation
    val result = csvInput.map(line => {
      val regex = "[\"]{3}.*[\"]{3}".r
      regex.findFirstMatchIn(line) match {
        case Some(m) => m.group(0).replace("\"", "").replace("\\", "")
        case None => ""
      }
    }).filter(line => line.nonEmpty).distinct()
    // Sink
    result.writeAsText("out/log/audit/batch/parameters", FileSystem.WriteMode.OVERWRITE).setParallelism(1)
    result.print()
  }

  @Test
  def regularExpression(): Unit = {
    val line = "2019-05-11 09:24:49,/t3-admin/admin/order/queryOrderListByPage,zuul-center,\"80\",Nanjing,AS,CN,CN,China,32.0625,118.75,,\"32\",Jiangsu,Asia/Shanghai,2019-05-11 09:24:49,POST,\"\"\"{\\\"\"currPage\\\"\":1,\\\"\"pageSize\\\"\":10,\\\"\"createdTime\\\"\":[\\\"\"\\\"\",\\\"\"\\\"\"]}\"\"\",\"200\",\"47.111.32.53\",,t3-admin,http://47.111.32.53/t3-admin/admin/order/queryOrderListByPage,\"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\",\"2f703bfe7f6d4900b81dbbc4b3bcac9c\",\"1\""
    val regex = "[\"]{3}(.*)[\"]{3}".r
    print(regex.findFirstMatchIn(line).get.group(1))
  }

}
