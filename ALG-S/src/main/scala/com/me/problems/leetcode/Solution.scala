package com.me.problems.leetcode

object Solution {

  def main(args: Array[String]): Unit = {
    //    println(evalRPN(List("2", "1", "+", "3", "*")))
    //    println(evalRPN(List("4", "13", "5", "/", "+")))

    //(-4,-4),(-8,-582),(-3,3),(-9,-651),(9,591)
//    val points = Array((0,-12),(5,2),(2,5),(0,-5),(1,5),(2,-2),(5,-4),(3,4),(-2,4),(-1,4),(0,-5),(0,-8),(-2,-1),(0,-11),(0,-9)).map(Point.fromTuple(_))
//    println(maxPoints(points))
    
    println(reverseBetween(List(1, 2, 3, 4, 5), 1, 4))
  }

  def evalRPN(expr: List[String]): Int = {

    def fr(pre: List[Int], tail: List[String]): Int = tail match {
      case Nil => pre.head
      case "+" :: tail =>
        val a = pre.head
        val b = pre.tail.head
        fr((a + b) :: pre.tail.tail, tail)
      case "-" :: tail =>
        val a = pre.head
        val b = pre.tail.head
        fr((b - a) :: pre.tail.tail, tail)
      case "*" :: tail =>
        val a = pre.head
        val b = pre.tail.head
        fr((a * b) :: pre.tail.tail, tail)
      case "/" :: tail =>
        val a = pre.head
        val b = pre.tail.head
        fr((b / a) :: pre.tail.tail, tail)
      case x :: tail =>
        fr(x.toInt :: pre, tail)
    }

    fr(Nil, expr)
  }

  case class Point(x: Int, y: Int) {
    def inLine(a: Point, b: Point) = (x - b.x) * (b.y - a.y) == (b.x - a.x) * (y - b.y)
  }

  object Point {
    def apply() = new Point(0, 0)
    implicit def fromTuple(x: (Int, Int)) = new Point(x._1, x._2)
  }

  class Line(val a: Point, val b: Point, var nodes: Int = 2) {
    def inLine(c: Point) = c.inLine(a, b)
  }

  object Line {
    def apply(a: Point, b: Point) = new Line(a, b)
  }
  def maxPoints(ps: Array[Point]): Int = {
    if (ps.length <= 2) {
      ps.length
    } else {
      val points = ps.toList

      def fr(lines: List[Line], ps: List[Point]): List[Line] = ps match {
        case Nil => lines
        case p :: tail =>
          val (x, y) = lines.partition(_.inLine(p))
          x.foreach(l => l.nodes += 1)
          fr(lines ::: y.flatMap(l => {
            (Line(l.a, p)) :: (Line(l.b, p)) :: Nil
          }), tail)
      }

      val a = points.head
      val b = points.tail.head
      
      
      val lines = fr(List(Line(a, b)), points.tail.tail)
      
      lines.foldLeft(0) {
        case (m, l) => math.max(m, l.nodes)
      }
    }
  }
  
  def reverseBetween[T](list: List[T], m: Int, n: Int): List[T] =  {
    def fr(pre: List[T], mid: List[T], list: List[T], m: Int, n: Int): List[T] = (m, n) match {
      case (0, 0) => pre ::: mid ::: list
      case (0, y) =>
        val h = list.head
        val tail = list.tail
        fr(pre, h :: mid, tail, 0, y - 1)
      case (x, y) =>
        val h = list.head
        val tail = list.tail
        fr(pre :+ h, mid, tail, x - 1, y - 1)
    }
    
    fr(Nil, Nil, list, m - 1, n)
  }
}