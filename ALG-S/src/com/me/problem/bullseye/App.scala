package com.me.problem.bullseye

object App extends App {

  def draw(r: Long, k: Long, left: Long): Long = {
    val used = (r << 1) + (k << 2) + 1
    if(used > left) {
      k
    } else {
      draw(r, k + 1, left - used)
    }
  }
  
  var tl = readLine
  while(tl != null) {
    val t = tl.toInt
    for(i <- 0 until t) {
      val ry = readLine
      val rySplitted = ry.split(" ")
      val r = rySplitted(0).toLong
      val y = rySplitted(1).toLong
     
      val k = draw(r, 0, y)
      println(s"Case #${i + 1}: $k")
    }
    
    tl = readLine
  }
}