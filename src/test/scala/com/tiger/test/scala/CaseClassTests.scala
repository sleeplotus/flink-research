package com.tiger.test.scala

import org.junit.Test

/**
 * @author Created by SleepLotus on 2019-08-15
 */
class CaseClassTests {

  @Test
  def caseClassTest1():Unit = {
    case class Test(str:String, int:Int)

    val test = Test("Default", 0)
    val Test(str, int) = test
    println(str+":"+int)
    val Test(str1, 0) = test
    println(str1+":0")
  }

  @Test
  def caseClassTest2():Unit = {
    case class Test(str:String, int:Int)

    val test = Test("Default", 0)
    val Test(str, 1) = test
    println(str+":1")
  }

}
