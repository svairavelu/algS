package com.me.problem.fairandsqure

object App extends App {

 
  def palindrome(num: Long): Boolean = {
    val numbers = num.toString.toCharArray
    val l = numbers.length
    val n = l / 2
    var valid = true
    for(i <- 0 to n if valid) {
      val a = numbers(i)
      val b = numbers(l - i - 1)
      valid = (a == b)
    }
    valid
  }
  
  def sqrt(num: Long): (Long, Double) = {
    val a = Math.sqrt(num toDouble)
    (a.toLong, a - a.toLong)
  }
  
  def nFairAndSquare(a: Long, b: Long): Int = {
    val (as, al) = sqrt(a)
    val start = if(al > 0) as + 1 else as
    
    val (be, bl) = sqrt(b)
    val end = be
    var count = 0
    for(i <- start to end) {
      if(palindrome(i) && palindrome(i * i)) {
        count += 1
      }
    }
    
    count
  }
  
  
  
  
  var tl = readLine
  if(tl != null) {
    val t = tl toInt
    
    for(i <- 0 until t) {
      val line = readLine
      val nm = line.split(" ")
      val n = nm(0) toLong
      val m = nm(1) toLong
      
      val rt = nFairAndSquare(n, m)
      println(s"Case #${i + 1}: $rt")
      
    }
  }
  
}