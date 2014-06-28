package codejam.year2004.round2

import scala.io.Source
import scala.collection.mutable.Queue

object DontBreakTheNile2 extends App {

  val file = "src/main/scala/codejam/year2004/round2/C-large-practice.in"

  val lines = Source.fromFile(file).getLines()

  val T = lines.next().toInt

  def process(t: Int): Unit =
    if (t <= T) {
      val line = lines.next().split("\\s+").map(_.toInt)
      val w = line(0)
      val h = line(1)
      val b = line(2)

      trait Block {
        def dist(that: Block): Int
      }
      case class Building(x0: Int, y0: Int, x1: Int, y1: Int) extends Block {

        private def distX(that: Building) =
          if (x0 <= that.x1 && that.x0 <= x1) 0
          else if (x1 < that.x0) that.x0 - x1 - 1
          else x0 - that.x1 - 1

        private def distY(that: Building) =
          if (y0 <= that.y1 && that.y0 <= y1) 0
          else if (y1 < that.y0) that.y0 - y1 - 1
          else y0 - that.y1 - 1

        def dist(that: Block) = that match {
          case LeftBank => x0
          case RightBank => w - 1 - x1
          case b: Building => distX(b) max distY(b)
        }
      }
      case object LeftBank extends Block {
        def dist(that: Block) = that match {
          case b: Building => b.dist(this)
          case RightBank => w
        }
      }
      case object RightBank extends Block {
        def dist(that: Block) = that match {
          case b: Building => b.dist(this)
          case LeftBank => w
        }
      }

      def readBlock(i: Int, blocks: Map[Int, Block]): Map[Int, Block] =
        if (i == b + 2) blocks
        else if (i == b + 1) readBlock(i + 1, blocks + (i -> RightBank))
        else if (i == b) readBlock(i + 1, blocks + (i -> LeftBank))
        else {
          val cord = lines.next().split("\\s+").map(_.toInt)
          readBlock(i + 1, blocks + (i -> Building(cord(0), cord(1), cord(2), cord(3))))
        }

      val blocks = readBlock(0, Map.empty)
      val grid = Array.fill(b + 2)(Array.fill(b + 2)(0))

      for {
        i <- 0 until b + 2
        a = blocks(i)
        j <- (i + 1) until b + 2
        b = blocks(j)
        d = a.dist(b)
      } {
        grid(i)(j) = d
        grid(j)(i) = d
      }

      def min(xs: Array[Int], i: Int, m: Int, checked: Set[Int]): Int =
        if (i >= xs.length) m
        else {
          if (checked.contains(i)) min(xs, i + 1, m, checked)
          else if (m < 0 || xs(m) > xs(i)) min(xs, i + 1, i, checked)
          else min(xs, i + 1, m, checked)
        }

      def bfs(dist: Array[Int], checked: Set[Int]): Int = {
        val i = min(dist, 0, -1, checked)
        if (i == b + 1) dist(i)
        else {
          val v = dist(i)
          for {
            j <- 0 until b + 2
            if (j != i && !checked.contains(j))
          } {
            if (v + grid(i)(j) < dist(j)) {
              dist(j) = v + grid(i)(j)
            }
          }
          bfs(dist, checked + i)
        }
      }

      val dist = Array.fill(b + 2)(w)
      dist(b) = 0

      val minCut = bfs(dist, Set.empty)

      println(s"Case #$t: $minCut")

      process(t + 1)
    }

  process(1)

}  