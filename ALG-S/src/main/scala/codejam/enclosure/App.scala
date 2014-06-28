package codejam.enclosure

import scala.io.Source

object App extends App {

  val lines = Source.stdin.getLines

  val T = lines.next.toInt

  val INF = 100000
  var cache = Map.empty[(Int, Int, Int), Int]
  def dp(prev: Int, remPoints: Int, remRows: Int, m: Int): Int =
    if (cache.contains((prev, remPoints, remRows))) cache((prev, remPoints, remRows))
    else {
      val res =
        if (remPoints <= 0) 0
        else if (remRows <= 0) INF
        else if (m == 1) remPoints
        else {
          ((prev - 2 max 1) to (prev + 2 min m)).foldLeft(INF)(
            (r, x) =>
              if (x >= remPoints) { r min x }
              else if (x > 1) {
                r min (2 + dp(x, remPoints - x, remRows - 1, m))
              } else r)
        }
      cache += (prev, remPoints, remRows) -> res
      res
    }
  def cal(n: Int, m: Int, k: Int): Int =
    if (n < m) cal(m, n, k)
    else {
      (1 to (k min m)).foldLeft(INF)((r, x) => (r min (x + dp(x, k - x, n - 1, m))))
    }

  def process(t: Int): Unit =
    if (t <= T) {
      val line = lines.next.split("\\s+").map(_.toInt)
      println(s"Case #$t: ${cal(line(0), line(1), line(2))}")
      process(t + 1)
    }

  process(1)
}