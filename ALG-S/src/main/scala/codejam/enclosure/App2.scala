package codejam.enclosure

import scala.io.Source

object App2 extends App {

  val lines = Source.stdin.getLines

  val T = lines.next.toInt

  def emptyTriangle(side: Int) = (side * (side + 1)) / 2
  def cal(n: Int, m: Int, k: Int): Int =
    if (n > m) cal(m, n, k)
    else {
      var best = k
      for {
        r <- 2 to n
        c <- r to m
        if (r * c >= k)
        i <- 1 until 2 * r
        x = r * c - emptyTriangle(i / 4) - emptyTriangle((i + 1) / 4) - emptyTriangle((i + 2) / 4) - emptyTriangle((i + 3) / 4)
        if (x >= k)
      } {
        best = best min (2 * (r + c) - 4 - i)
      }

      best
    }

  def process(t: Int): Unit =
    if (t <= T) {
      val line = lines.next.split("\\s+").map(_.toInt)
      println(s"Case #$t: ${cal(line(0), line(1), line(2))}")
      process(t + 1)
    }
  
  process(1)
}