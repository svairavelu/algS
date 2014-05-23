package com.me.problems.leetcode

object CombinationSumS extends App {

  def combinationSum(xs: Array[Int], target: Int): List[List[Int]] = {
    val sortedXs = xs.sorted
    var set = Set.empty[List[Int]]
    def f(x: Int, xs: List[Int]): List[List[Int]] = {
      if (x == 0) {
        //a valid path, not exclude 0
        List(xs)
      } else {
        sortedXs.filter(_ <= x).flatMap(y => f(x - y, y :: xs)).toList.map(_.sorted)
      }
    }

    f(target, Nil).distinct
  }

  combinationSum(Array(2, 3, 6, 7), 7) foreach println
}