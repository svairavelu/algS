package com.me.problems.leetcode

object LongestPalindromicSubString extends App {

  def extractPalindromes(str: String): List[String] = {

    //@brief Collect nested palindromes increasing by 1 char at each end.
    def collectIncreasingR(list: List[String], from: Int, until: Int): List[String] =
      if (from < 0 || until > str.size //Out of bounds
        || str(from) != str(until - 1)) list
      else collectIncreasingR(str.slice(from, until) :: list, from - 1, until + 1)

    //@brief Start to collect from given size, for each position
    def collect(n: Int) = (0 to str.size - n) flatMap
      (i => collectIncreasingR(Nil, i, i + n))

    (collect(2) ++ collect(3)).toList // Even sized then odd sized palindromes
  }

  extractPalindromes("ababa") foreach println
}