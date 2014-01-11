package com.me.scalaz

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(81); 
  println("Welcome to the Scala worksheet")

  import scalaz._
  import std.option._, std.list._;$skip(170); val res$0 =  // functions and type class instances for Option and List
  Apply[Option].apply2(some(1), some(2))((a, b) => a + b);System.out.println("""res0: Option[Int] = """ + $show(res$0));$skip(55); val res$1 = 
  Traverse[List].traverse(List(1, 2, 3))(i => some(i))

	import syntax.bind._;System.out.println("""res1: Option[List[Int]] = """ + $show(res$1));$skip(59); val res$2 = 
	
	List(List(1), List(2, 3)).join;System.out.println("""res2: List[Int] = """ + $show(res$2));$skip(37); val res$3 = 
	
	List(List(1), List(2, 3)).flatten;System.out.println("""res3: List[Int] = """ + $show(res$3))}
}
