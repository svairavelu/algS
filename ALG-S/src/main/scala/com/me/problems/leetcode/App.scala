package com.me.problems.leetcode

object App extends App {
  class A {
    var message = "hello"
    def f = this.message
  }

  object B extends A
  object C extends A

  println(B.f)
  println(C.f)
  B.message = "bye"
  
  println(B.f)
  println(C.f)
  
}