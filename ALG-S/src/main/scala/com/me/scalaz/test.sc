package com.me.scalaz

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  import scalaz._
  import std.option._, std.list._ // functions and type class instances for Option and List
  Apply[Option].apply2(some(1), some(2))((a, b) => a + b)
                                                  //> res0: Option[Int] = Some(3)
  Traverse[List].traverse(List(1, 2, 3))(i => some(i))
                                                  //> res1: Option[List[Int]] = Some(List(1, 2, 3))
}