package com.me.problems.leetcode
/**
 * Given a collection of integers that might contain duplicates, S, return all possible subsets.
 *
 * Note:
 * Elements in a subset must be in non-descending order.
 * The solution set must not contain duplicate subsets.
 * For example,
 * If S = [1,2,2], a solution is:
 *
 * [
 * [2],
 * [1],
 * [1,2,2],
 * [2,2],
 * [1,2],
 * []
 * ]
 * Discuss
 *
 *
 */
object SubSetIIS extends App {

  def subsetsWithDup(xs: Int*): List[List[Int]] = {
    var cache = Map.empty[(Int, Int), List[List[Int]]]

    def fx(s: Int, e: Int): List[List[Int]] = {
      if (s == e) List(Nil)
      else if (s + 1 == e) List(List(xs(s)), Nil)
      else {
        var result = List.empty[List[Int]]
        result = Nil :: result
        var set = Set.empty[List[Int]]
        set += Nil
        for {
          i <- s + 1 until e
          left = fx(s, i)
          right = fx(i, e)
          l <- left
          r <- right
        } {
          val list = l ::: r
          if (!set.contains(list)) {
            set += list
            result = list :: result
          }
        }
        cache += (s, e) -> result
        result
      }
    }
    fx(0, xs.length)
  }

  subsetsWithDup(1, 2, 2) foreach println
}