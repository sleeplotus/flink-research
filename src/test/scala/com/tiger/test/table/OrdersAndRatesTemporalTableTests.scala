package com.tiger.test.table

import java.sql.{Date, Timestamp}

import com.tiger.test.table.functions.timestamps.{OrderDateExtractor, OrderTimestampExtractor}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.Types
import org.apache.flink.table.api.scala._
import org.apache.flink.table.sinks.CsvTableSink
import org.apache.flink.types.Row
import org.junit.Test

import scala.collection.mutable

/**
  * @author 王澎
  */
class OrdersAndRatesTemporalTableTests {

  @Test
  def ordersAndRatesTemporalEventTimeByTimestamp(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)
    env.setParallelism(1)
    // 设置时间类型是 event-time  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // When you don't set TimeCharacteristic, The default value of TimeCharacteristic is TimeCharacteristic.ProcessingTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // 构造订单数据
    val ordersData = new mutable.MutableList[(Long, String, Timestamp)]
    ordersData.+=((2L, "Euro", new Timestamp(2L)))
    ordersData.+=((1L, "US Dollar", new Timestamp(3L)))
    ordersData.+=((50L, "Yen", new Timestamp(4L)))
    ordersData.+=((3L, "Euro", new Timestamp(5L)))

    //构造汇率数据
    val ratesHistoryData = new mutable.MutableList[(String, Long, Timestamp)]
    ratesHistoryData.+=(("US Dollar", 102L, new Timestamp(1L)))
    ratesHistoryData.+=(("Euro", 114L, new Timestamp(1L)))
    ratesHistoryData.+=(("Yen", 1L, new Timestamp(1L)))
    ratesHistoryData.+=(("Euro", 116L, new Timestamp(5L)))
    ratesHistoryData.+=(("Euro", 119L, new Timestamp(7L)))

    // 进行订单表 event-time 的提取
    val orders = env
                 .fromCollection(ordersData)
                 .assignTimestampsAndWatermarks(new OrderTimestampExtractor[Long, String]())
                 .toTable(tEnv, 'amount, 'currency, 'rowtime.rowtime)

    // 进行汇率表 event-time 的提取
    val ratesHistory = env
                       .fromCollection(ratesHistoryData)
                       .assignTimestampsAndWatermarks(new OrderTimestampExtractor[String, Long]())
                       .toTable(tEnv, 'currency, 'rate, 'rowtime.rowtime)

    // 注册订单表和汇率表
    tEnv.registerTable("Orders", orders)
    tEnv.registerTable("RatesHistory", ratesHistory)
    val tab = tEnv.scan("RatesHistory")
    // 创建TemporalTableFunction
    val temporalTableFunction = tab.createTemporalTableFunction('rowtime, 'currency)
    //注册TemporalTableFunction
    tEnv.registerFunction("Rates", temporalTableFunction)

    val sqlQuery =
      """
        |SELECT o.currency, o.amount, r.rate, o.rowtime as order_rowtime, r.rowtime as rate_rowtime,
        |  o.amount * r.rate AS yen_amount
        |FROM
        |  Orders AS o,
        |  LATERAL TABLE (Rates(o.rowtime)) AS r
        |WHERE r.currency = o.currency
        |""".stripMargin

    tEnv.registerTable("TemporalJoinResult", tEnv.sqlQuery(sqlQuery))

    val result = tEnv.scan("TemporalJoinResult").toAppendStream[Row]

    // 打印查询结果
    result.print()

    // Defines table sink.
    val csvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/temporal_join_result_by_event_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "CsvOutputTable",
      // specify table schema
      Array[String]("currency", "amount", "rate", "order_rowtime", "rate_rowtime", "yen_amount"),
      Array[TypeInformation[_]](Types.STRING, Types.LONG, Types.LONG, Types.SQL_TIMESTAMP, Types.SQL_TIMESTAMP,
                                Types.LONG),
      csvSink)
    // Insert table into table sink.
    tEnv.scan("TemporalJoinResult").insertInto("CsvOutputTable")

    env.execute()
  }

  @Test
  def ordersAndRatesTemporalEventTimeByDate(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)
    env.setParallelism(1)
    // 设置时间类型是 event-time  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // When you don't set TimeCharacteristic, The default value of TimeCharacteristic is TimeCharacteristic.ProcessingTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // 构造订单数据
    val ordersData = new mutable.MutableList[(Long, String, Date)]
    ordersData.+=((2L, "Euro", new Date(2L)))
    ordersData.+=((1L, "US Dollar", new Date(3L)))
    ordersData.+=((50L, "Yen", new Date(4L)))
    ordersData.+=((3L, "Euro", new Date(5L)))

    //构造汇率数据
    val ratesHistoryData = new mutable.MutableList[(String, Long, Date)]
    ratesHistoryData.+=(("US Dollar", 102L, new Date(1L)))
    ratesHistoryData.+=(("Euro", 114L, new Date(1L)))
    ratesHistoryData.+=(("Yen", 1L, new Date(1L)))
    ratesHistoryData.+=(("Euro", 116L, new Date(5L)))
    ratesHistoryData.+=(("Euro", 119L, new Date(7L)))

    // 进行订单表 event-time 的提取
    val orders = env
                 .fromCollection(ordersData)
                 .assignTimestampsAndWatermarks(new OrderDateExtractor[Long, String]())
                 .toTable(tEnv, 'amount, 'currency, 'rowtime.rowtime)

    // 进行汇率表 event-time 的提取
    val ratesHistory = env
                       .fromCollection(ratesHistoryData)
                       .assignTimestampsAndWatermarks(new OrderDateExtractor[String, Long]())
                       .toTable(tEnv, 'currency, 'rate, 'rowtime.rowtime)

    // 注册订单表和汇率表
    tEnv.registerTable("Orders", orders)
    tEnv.registerTable("RatesHistory", ratesHistory)
    val tab = tEnv.scan("RatesHistory")
    // 创建TemporalTableFunction
    val temporalTableFunction = tab.createTemporalTableFunction('rowtime, 'currency)
    //注册TemporalTableFunction
    tEnv.registerFunction("Rates", temporalTableFunction)

    val sqlQuery =
      """
        |SELECT o.currency, o.amount, r.rate, o.rowtime as order_rowtime, r.rowtime as rate_rowtime,
        |  o.amount * r.rate AS yen_amount
        |FROM
        |  Orders AS o,
        |  LATERAL TABLE (Rates(o.rowtime)) AS r
        |WHERE r.currency = o.currency
        |""".stripMargin

    tEnv.registerTable("TemporalJoinResult", tEnv.sqlQuery(sqlQuery))

    val result = tEnv.scan("TemporalJoinResult").toAppendStream[Row]

    // 打印查询结果
    result.print()

    // Defines table sink.
    val csvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/temporal_join_result_by_event_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "CsvOutputTable",
      // specify table schema
      Array[String]("currency", "amount", "rate", "order_rowtime", "rate_rowtime", "yen_amount"),
      Array[TypeInformation[_]](Types.STRING, Types.LONG, Types.LONG, Types.SQL_TIMESTAMP, Types.SQL_TIMESTAMP,
                                Types.LONG),
      csvSink)
    // Insert table into table sink.
    tEnv.scan("TemporalJoinResult").insertInto("CsvOutputTable")

    env.execute()
  }


  @Test
  def ordersAndRatesTemporalProcessTime(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)
    env.setParallelism(1)
    // 设置时间类型是 process-time  env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    // 构造订单数据
    val ordersData = new mutable.MutableList[(Long, String)]
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))


    //构造汇率数据
    val ratesHistoryData = new mutable.MutableList[(String, Long)]
    ratesHistoryData.+=(("US Dollar", 102L))
    ratesHistoryData.+=(("Euro", 114L))
    ratesHistoryData.+=(("Yen", 1L))
    ratesHistoryData.+=(("Euro", 116L))
    ratesHistoryData.+=(("Euro", 119L))

    // 订单表
    val orders = env
                 .fromCollection(ordersData)
                 .toTable(tEnv, 'amount, 'currency, 'process_time.proctime)

    // Defines table sink.
    val ordersCsvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/orders_by_process_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "OrdersCsvOutputTable",
      // specify table schema
      Array[String]("amount", "currency", "process_time"),
      Array[TypeInformation[_]](Types.LONG, Types.STRING, Types.SQL_TIMESTAMP),
      ordersCsvSink)
    // Insert table into table sink.
    orders.insertInto("OrdersCsvOutputTable")

    // 汇率表
    val ratesHistory = env
                       .fromCollection(ratesHistoryData)
                       .toTable(tEnv, 'currency, 'rate, 'process_time.proctime)

    // 注册订单表和汇率表
    tEnv.registerTable("Orders", orders)
    tEnv.registerTable("RatesHistory", ratesHistory)
    val tab = tEnv.scan("RatesHistory")
    // 创建TemporalTableFunction
    val temporalTableFunction = tab.createTemporalTableFunction('process_time, 'currency)
    //注册TemporalTableFunction
    tEnv.registerFunction("Rates", temporalTableFunction)

    val sqlQuery =
      """
        |SELECT o.currency, o.amount, r.rate, o.process_time as order_process_time, r.process_time as rate_process_time,
        |  o.amount * r.rate AS yen_amount
        |FROM
        |  Orders AS o,
        |  LATERAL TABLE (Rates(o.process_time)) AS r
        |WHERE r.currency = o.currency
        |""".stripMargin

    tEnv.registerTable("TemporalJoinResult", tEnv.sqlQuery(sqlQuery))

    val result = tEnv.scan("TemporalJoinResult").toAppendStream[Row]
    // 打印查询结果
    result.print()

    // Defines table sink.
    val csvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/temporal_join_result_by_process_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "CsvOutputTable",
      // specify table schema
      Array[String]("currency", "amount", "rate", "order_process_time", "rate_process_time", "yen_amount"),
      Array[TypeInformation[_]](Types.STRING, Types.LONG, Types.LONG, Types.SQL_TIMESTAMP, Types.SQL_TIMESTAMP,
                                Types.LONG),
      csvSink)
    // Insert table into table sink.
    tEnv.scan("TemporalJoinResult").insertInto("CsvOutputTable")

    env.execute()
  }

  @Test
  def temporalTableProcessTime(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)
    env.setParallelism(1)
    // 设置时间类型是 process-time  env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    // 构造订单数据
    val ordersData = new mutable.MutableList[(Long, String)]
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))

    //构造汇率数据
    val ratesHistoryData = new mutable.MutableList[(String, Long)]
    ratesHistoryData.+=(("US Dollar", 102L))
    ratesHistoryData.+=(("Euro", 114L))
    ratesHistoryData.+=(("Yen", 1L))
    ratesHistoryData.+=(("Euro", 116L))
    ratesHistoryData.+=(("Euro", 119L))

    // 订单表
    val orders = env
                 .fromCollection(ordersData)
                 .toTable(tEnv, 'amount, 'currency, 'process_time.proctime)

    // Defines table sink.
    val ordersCsvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/orders_by_process_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "OrdersCsvOutputTable",
      // specify table schema
      Array[String]("amount", "currency", "process_time"),
      Array[TypeInformation[_]](Types.LONG, Types.STRING, Types.SQL_TIMESTAMP),
      ordersCsvSink)
    // Insert table into table sink.
    orders.insertInto("OrdersCsvOutputTable")

    // 汇率表
    val ratesHistory = env
                       .fromCollection(ratesHistoryData)
                       .toTable(tEnv, 'currency, 'rate, 'process_time.proctime)

    // Defines table sink.
    val ratesCsvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/rates_by_process_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "RatesCsvOutputTable",
      // specify table schema
      Array[String]("currency", "rate", "process_time"),
      Array[TypeInformation[_]](Types.STRING, Types.LONG, Types.SQL_TIMESTAMP),
      ratesCsvSink)
    // Insert table into table sink.
    ratesHistory.insertInto("RatesCsvOutputTable")

    // 注册订单表和汇率表
    tEnv.registerTable("Orders", orders)
    tEnv.registerTable("RatesHistory", ratesHistory)
    val tab = tEnv.scan("RatesHistory")
    // 创建TemporalTableFunction
    val temporalTableFunction = tab.createTemporalTableFunction('process_time, 'currency)
    //注册TemporalTableFunction
    tEnv.registerFunction("Rates", temporalTableFunction)

    val sqlQuery =
      """
        |SELECT o.currency, o.amount, r.rate, o.process_time as order_process_time, r.process_time as rate_process_time,
        |  o.amount * r.rate AS yen_amount
        |FROM
        |  Orders AS o,
        |  LATERAL TABLE (Rates(o.process_time)) AS r
        |WHERE r.currency = o.currency
        |""".stripMargin

    tEnv.registerTable("TemporalJoinResult", tEnv.sqlQuery(sqlQuery))

    val result = tEnv.scan("TemporalJoinResult").toAppendStream[Row]
    // 打印查询结果
    result.print()

    // Defines table sink.
    val csvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/temporal_join_result_by_process_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "CsvOutputTable",
      // specify table schema
      Array[String]("currency", "amount", "rate", "order_process_time", "rate_process_time", "yen_amount"),
      Array[TypeInformation[_]](Types.STRING, Types.LONG, Types.LONG, Types.SQL_TIMESTAMP, Types.SQL_TIMESTAMP,
                                Types.LONG),
      csvSink)
    // Insert table into table sink.
    tEnv.scan("TemporalJoinResult").insertInto("CsvOutputTable")

    env.execute()
  }

  @Test
  def ordersProcessTimeDelay(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)
    //    env.setParallelism(1)
    // 设置时间类型是 process-time  env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    // 构造订单数据
    val ordersData = new mutable.MutableList[(Long, String)]
    ordersData.+=((2L, "Euro"))
    ordersData.+=((1L, "US Dollar"))
    ordersData.+=((50L, "Yen"))
    ordersData.+=((3L, "Euro"))


    //构造汇率数据
    val ratesHistoryData = new mutable.MutableList[(String, Long)]
    ratesHistoryData.+=(("US Dollar", 102L))
    ratesHistoryData.+=(("Euro", 114L))
    ratesHistoryData.+=(("Yen", 1L))
    ratesHistoryData.+=(("Euro", 116L))
    ratesHistoryData.+=(("Euro", 119L))

    // 订单表
    val orders = env
                 .fromCollection(ordersData).setParallelism(1)
                 .toTable(tEnv, 'amount, 'currency, 'process_time.proctime)

    // 汇率表
    val ratesHistory = env
                       .fromCollection(ratesHistoryData)
                       .toTable(tEnv, 'currency, 'rate, 'process_time.proctime)

    // 注册订单表和汇率表
    tEnv.registerTable("Orders", orders)
    tEnv.registerTable("RatesHistory", ratesHistory)
    val tab = tEnv.scan("RatesHistory")
    // 创建TemporalTableFunction
    val temporalTableFunction = tab.createTemporalTableFunction('process_time, 'currency)
    //注册TemporalTableFunction
    tEnv.registerFunction("Rates", temporalTableFunction)

    val sqlQuery =
      """
        |SELECT o.currency, o.amount, r.rate, o.process_time as order_process_time, r.process_time as rate_process_time,
        |  o.amount * r.rate AS yen_amount
        |FROM
        |  Orders AS o,
        |  LATERAL TABLE (Rates(o.process_time)) AS r
        |WHERE r.currency = o.currency
        |""".stripMargin

    tEnv.registerTable("TemporalJoinResult", tEnv.sqlQuery(sqlQuery))

    val result = tEnv.scan("TemporalJoinResult").toAppendStream[Row]
    // 打印查询结果
    result.print()

    // Defines table sink.
    val csvSink: CsvTableSink = new CsvTableSink(
      "out/log/audit/streaming/temporal_join_result_by_process_time", // output path
      fieldDelim = ",", // optional: delimit files by '|'
      numFiles = 1, // optional: write to a single file
      writeMode = WriteMode.OVERWRITE) // optional: override existing files
    // Registers table sink.
    tEnv.registerTableSink(
      "CsvOutputTable",
      // specify table schema
      Array[String]("currency", "amount", "rate", "order_process_time", "rate_process_time", "yen_amount"),
      Array[TypeInformation[_]](Types.STRING, Types.LONG, Types.LONG, Types.SQL_TIMESTAMP, Types.SQL_TIMESTAMP,
                                Types.LONG),
      csvSink)
    // Insert table into table sink.
    tEnv.scan("TemporalJoinResult").insertInto("CsvOutputTable")

    env.execute()
  }

}
