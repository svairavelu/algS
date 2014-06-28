package codejam.year2004.round2

import scala.io.Source

object DontBreakTheNileSmall extends App {

  val file = "src/main/scala/codejam/year2004/round2/C-small-practice.in"

  val lines = Source.fromFile(file).getLines()

  val T = lines.next().toInt

  val neighbors = Array((0, -1), (1, 0), (0, 1), (-1, 0))
  val directionMap = Map(0 -> Array(3, 0, 1), 1 -> Array(0, 1, 2), 2 -> Array(1, 2, 3), 3 -> Array(2, 3, 0))

  def process(t: Int): Unit =
    if (t <= T) {
      val line = lines.next().split("\\s+").map(_.toInt)
      val w = line(0)
      val h = line(1)
      val b = line(2)

      val grid = Array.fill(h)(Array.fill(w)(0))

      def fill(x: Int): Unit =
        if (x < b) {
          val cord = lines.next().split("\\s+").map(_.toInt)
          val x0 = cord(0)
          val y0 = cord(1)
          val x1 = cord(2)
          val y1 = cord(3)

          for {
            i <- y0 to y1
            j <- x0 to x1
          } {
            grid(i)(j) = -1
          }

          fill(x + 1)
        }

      fill(0)

      def nextCell(i: Int, j: Int, dir: Int): Option[(Int, Int, Int)] = {
        val dirs = directionMap(dir)
        dirs.find(d => {
          val (di, dj) = neighbors(d)
          val (x, y) = (i + di, j + dj)
          (x >= 0 && x < h && y >= 0 && y < w && grid(x)(y) == 0)
        }).map(d => {
          val (di, dj) = neighbors(d)
          (i + di, j + dj, d)
        })
      }

      def flow(i: Int, j: Int, dir: Int, path: List[(Int, Int, Int)]): Boolean =
        if (i == h - 1) true
        else {
          nextCell(i, j, dir) match {
            case Some((x, y, d)) =>
              grid(x)(y) = 1
              flow(x, y, d, (i, j, dir) :: path)
            case None if path.isEmpty => false
            case None =>
              val (x, y, d) = path.head
              flow(x, y, d, path.tail)
          }
        }

      val max =
        (0 until w).foldLeft(0)((cnt, j) => {
          if (grid(0)(j) == 0) {
            grid(0)(j) = 1
            if (flow(0, j, 1, Nil)) {
              cnt + 1
            } else cnt
          } else cnt
        })

      println(s"Case #$t: $max")
      process(t + 1)
    }

  process(1)
}  