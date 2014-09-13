package codejam.year2004.wf

import scala.io.Source

object A extends App {

  val file = Source.fromFile("src/main/scala/codejam/year2004/wf/A-large-practice.in").getLines()
  val T = file.next().toInt

  def play(grid: Array[Array[Int]]): Option[Int] = {
    def copy(xs: Array[Int])(fn: Int => Int): Array[Int] = {
      val ys = Array.fill(xs.length)(0)
      for {
        i <- 0 until xs.length
      } {
        ys(i) = fn(xs(i))
      }
      ys
    }

    def checkMatch(xs: Array[Int], ys: Array[Int]): Boolean = (xs.zip(ys)).filter(x => x._1 != x._2).size == 0

    def doPlay(i: Int, ma: Int, mb: Int, ca: Int, cb: Int, a: Array[Int], b: Array[Int]): Option[Int] =
      if (i >= grid.length && ca == cb) {
        Some(ma min mb)
      } else if (i >= grid.length) {
        None
      } else {
        val row = grid(i)
        if (checkMatch(row, a)) {
          doPlay(i + 1, if (i % 2 == 0) ma else ma + 1, mb, ca + 1, cb, a, b)
        } else if (checkMatch(row, b)) {
          doPlay(i + 1, ma, if (i % 2 == 0) mb else mb + 1, ca, cb + 1, a, b)
        } else {
          None
        }
      }

    val a = copy(grid(0))(x => x)
    val b = copy(grid(0))(x => 1 - x)

    doPlay(0, 0, 0, 0, 0, a, b)
  }

  def transf(grid: Array[Array[Int]]): Array[Array[Int]] = {
    val l = grid.length

    val xy = Array.fill(l, l)(0)

    for {
      i <- 0 until l
      j <- 0 until l
    } {
      xy(j)(i) = grid(i)(j)
    }

    xy
  }

  def process(t: Int): Unit =
    if (t <= T) {
      val n = file.next().toInt
      val grid = Array.fill(2 * n, 2 * n)(0)

      for {
        i <- 0 until (2 * n)
      } {
        val row = file.next().split("").map(_.toInt)
        grid(i) = row
      }

      val r =
        play(grid).flatMap(x => {
          play(transf(grid)).map(y => x + y)
        })

      r match {
        case Some(x) => println(s"Case #$t: $x")
        case None => println(s"Case #$t: IMPOSSIBLE")
      }

      process(t + 1)
    }

  process(1)
}