package com.me.scalaz

object day1 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(80); 
  println("Welcome to the Scala worksheet")
  
  import scalaz._
  import Scalaz._;$skip(55); val res$0 = 
  
  
  1 === 1;System.out.println("""res0: Boolean = """ + $show(res$0));$skip(16); val res$1 = 
  
  1 == "foo";System.out.println("""res1: Boolean = """ + $show(res$1));$skip(23); val res$2 = 
  
  1.some =/= 2.some;System.out.println("""res2: Boolean = """ + $show(res$2))}
  
  //1.assert_=== 2
  
}
