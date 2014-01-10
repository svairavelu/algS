package com.me.scalaz

object test {
  println("Welcome to the Scala worksheet")

  import scalaz._
  import std.option._, std.list._ // functions and type class instances for Option and List
  Apply[Option].apply2(some(1), some(2))((a, b) => a + b)
  Traverse[List].traverse(List(1, 2, 3))(i => some(i))

	import syntax.bind._
	
	List(List(1), List(2, 3)).join
	
	List(List(1), List(2, 3)).flatten
	
}