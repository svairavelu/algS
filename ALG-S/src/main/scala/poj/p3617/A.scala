package poj.p3617

import scala.io.Source

object A extends App {
  val file = Source.fromFile("src/main/scala/poj/p3617/A-console.in").getLines()
  val n = file.next().toInt
  val xs = Array.fill(n)("")
  for {
    i <- 0 until n
  } {
    xs(i) = file.next()
  }

  def min(a: String, b: String): String = {
    val cmp = a.compareTo(b)
    if (cmp <= 0) a
    else b
  }

  def rearrange(i: Int, j: Int, xs: Array[String], line: String): String = {
    if (i > j) line
    else {
      val a = xs(i)
      val b = xs(j)
      val cmp = a.compareTo(b)
      if (cmp < 0) rearrange(i + 1, j, xs, line + a)
      else if (cmp > 0) rearrange(i, j - 1, xs, line + b)
      else {
        val ar = rearrange(i + 1, j, xs, line + a)
        val br = rearrange(i, j - 1, xs, line + b)
        min(ar, br)
      }
    }
  }
  
  println(rearrange(0, n - 1, xs, ""))
}