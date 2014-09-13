package poj

import scala.io.Source

object P3069 extends App {
  
  val file = Source.fromFile("src/main/scala/poj/P3069-console.in").getLines()

  def process(r: Int, n: Int): Int = {
    val xs = file.next().split("\\s+").map(_.toInt).sorted

    def right(i: Int, ans: Int, p: Int): Int = {
      if (i >= n) ans
      else if (p + r >= xs(i)) right(i + 1, ans, p)
      else {
        left(i + 1, ans, xs(i))
      }
    }

    def left(i: Int, ans: Int, s: Int): Int = {
      if (i >= n) ans + 1
      else if (s + r >= xs(i)) left(i + 1, ans, s)
      else {
        right(i, ans + 1, xs(i - 1))
      }
    }

    left(1, 0, xs(0))
  }

  var line = file.next().split("\\s+").map(_.toInt)
  var r: Int = line(0)
  var n: Int = line(1)
  while (r >= 0 && n >= 0) {
    println(process(r, n))
    line = file.next().split("\\s+").map(_.toInt)
    r = line(0)
    n = line(1)
  }
}