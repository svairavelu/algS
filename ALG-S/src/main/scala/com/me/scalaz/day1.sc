package com.me.scalaz

object day1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  import scalaz._
  import Scalaz._
  
  
  1 === 1                                         //> res0: Boolean = true
  
  1 == "foo"                                      //> res1: Boolean = false
  
  1.some =/= 2.some                               //> res2: Boolean = true
  
  //1.assert_=== 2
  
}