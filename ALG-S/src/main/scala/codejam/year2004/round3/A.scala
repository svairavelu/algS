package codejam.year2004.round3

import scala.io.Source

object A extends App {

  val file = "src/main/scala/codejam/year2004/round3/A-large-practice.in"

  val lines = Source.fromFile(file).getLines()

  val T = lines.next().toInt

  def play(n: Int, p: Int, q: Int, r: Int, s: Int): Double = {
    val xs = Array.fill(n)(0L)

    xs.foldLeft(0)((i, x) => {
      xs(i) = (1L * i * p + q) % r + s
      i + 1
    })

    val as = Array.fill(n + 1)(0L)

    xs.foldLeft(0)((idx, x) => {
      if (idx > 0) {
        as(idx) = as(idx - 1) + xs(idx - 1)
      }
      idx + 1
    })
    as(n) = as(n - 1) + xs(n - 1)

    def left(z: Long, l: Int, r: Int, x: Option[Int]): Option[Int] =
      if (l > r) x
      else {
        val mid = (l + r) / 2
        if (as(mid) == z) Some(mid)
        else if (as(mid) < z) left(z, mid + 1, r, Some(mid))
        else left(z, l, mid - 1, x)
      }

    def findJ(z: Long, i: Int): Option[Int] = {
      def go(l: Int, r: Int): Option[Int] =
        if (l > r) None
        else {
          val j = (l + r) / 2
          val mid = as(j + 1) - as(i)
          val right = as(n) - as(j + 1)
          val (min, max) = if (mid > right) (right, mid) else (mid, right)
          if (max <= z) Some(j)
          else if (min > z) None
          else if (mid > right) go(l, j - 1)
          else go(j + 1, r)
        }
      go(i - 1, n - 1)
    }

    def bs(lz: Long, rz: Long, x: Option[(Int, Int)]): Option[(Int, Int)] =
      if (lz > rz) x
      else {
        val z = (lz + rz) / 2
        left(z, 0, n, None).flatMap(
          i => findJ(z, i) match {
            case Some(j) => bs(lz, z - 1, Some(i, j))
            case None => bs(z + 1, rz, x)
          })
      }

    bs(0, as(n), None) match {
      case Some((i, j)) =>
        val a = as(i)
        val b = as(j + 1) - as(i)
        val c = as(n) - as(j + 1)
        val m = a max b max c
        1.0 * (as(n) - m) / as(n)
      case None => throw new Exception("Should not happen")
    }
  }

  def process(t: Int): Unit =
    if (t <= T) {
      val line = lines.next.split("\\s+").map(_.toInt)
      val r = play(line(0), line(1), line(2), line(3), line(4))
      println(s"Case #$t: $r")
      process(t + 1)
    }

  process(1)
}