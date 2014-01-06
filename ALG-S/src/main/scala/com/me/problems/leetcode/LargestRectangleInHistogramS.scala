package com.me.problems.leetcode

object LargestRectangleInHistogramS extends App {

  def largestRectangleArea(height: Int*): Int = {
    val n = height.length
    if (n == 0) {
      0
    } else {
      def f(idx: Int, xs: List[(Int, Int)], max: Int, pos: Int): Int = {
        if (idx == n) {
          xs.foldLeft(max) {
            case (mx, (i, h)) =>
              math.max(mx, (n - i) * h)
          }
        } else {
          val h = height(idx)
          xs match {
            case Nil => f(idx + 1, (idx, h) :: xs, math.max(max, h), idx + 1)
            case x :: tail if (x._2 < h) => f(idx + 1, (pos, h) :: xs, math.max(max, h), idx + 1)
            case x :: tail =>
              val (i, h) = x
              val s = (idx - i) * h
              f(idx, tail, math.max(max, s), i)
          }
        }
      }

      f(0, Nil, 0, 0)
    }
  }

  println(largestRectangleArea(5, 5, 1, 7, 1, 1, 5, 2, 7, 6))
}