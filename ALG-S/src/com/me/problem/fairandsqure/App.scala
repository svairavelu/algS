package com.me.problem.fairandsqure

object App extends App {

  def nFairAndSqure(l: Int) = {
    if(l == 1) {
      3
    } else {
      val h = l / 2 - 1
      var n = 1 + (5 * h + Math.pow(h, 3).toInt) / 6
      if(l % 2 == 1) {
        n *= 2
        n += 1 + h
        n += 2
      } else {
        n += 1
      }
      n
    }
  }
  
  def fairAndSqure(n: Int) = {
    
  }
  
  var tl = readLine
  if(tl != null) {
    val t = tl toInt
    
    for(i <- 0 until t) {
      val line = readLine
      val nm = line.split(" ")
      val n = nm(0) toLong
      val m = nm(1) toLong
      
      
      
    }
  }
  
}