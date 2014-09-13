package poj

import scala.io.Source

object P3253 extends App {
  val file = Source.fromFile("src/main/scala/poj/P3253-console.in").getLines()
  val n = file.next().toInt
  val xs = (0 until n).foldLeft(Nil: List[Int])(
    (xs, i) => {
      file.next().toInt :: xs
    }).sorted

  def inst(x: Int, xs: List[Int]): List[Int] =
    xs match {
      case h :: tail if (h >= x) => x :: xs
      case h :: tail => h :: inst(x, tail)
      case Nil => List(x)
    }

  def process(xs: List[Int], ans: Long): Long =
    xs match {
      case Nil => ans
      case List(_) => ans
      case a :: b :: tail =>
        process(inst(a + b, tail), ans + a + b)
    }

  println(process(xs, 0))
}