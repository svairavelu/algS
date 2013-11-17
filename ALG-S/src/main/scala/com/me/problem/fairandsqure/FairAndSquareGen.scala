package com.me.problem.fairandsqure

import scala.collection.mutable.ListBuffer

object FairAndSquareGen extends App {

  def base(num: Int): Long = {
    var rt = 1

    for (i <- 1 until num) {
      rt *= 10
    }

    rt
  }

  def reverse(num: Long, base: Long, nbase: Int): Long = {
    val high = num / base
    val left = num % base
    high * nbase + (if (left == 0) 0 else (
      reverse(left, base / 10, nbase * 10)))
  }

  def palindromeGen(digits: Int): List[Long] = {
    if (digits == 1) {
      List(1, 2, 3)
    } else {
      val lb = new ListBuffer[Long]()

      val isEven = digits % 2 == 0

      val half = digits / 2

      val start = base(half)
      val end = start * 10

      for (i <- start until end) {
        if (isEven) {
          lb += ((i * start * 10) + reverse(i, start, 1))
        } else {
          for (j <- 0 to 9) {
            lb += ((i * 10) + j) * start * 10 + reverse(i, start, 1)
          }
        }
      }

      lb.toList
    }
  }

  def palindrome(num: Long): Boolean = {
    val numbers = num.toString.toCharArray
    val l = numbers.length
    val n = l / 2
    var valid = true
    for (i <- 0 to n if valid) {
      val a = numbers(i)
      val b = numbers(l - i - 1)
      valid = (a == b)
    }
    valid
  }

  var rt = List.empty[Long]
  for (i <- 1 to 7) {
    val list = palindromeGen(i)
    list.foreach(n => {
      val square = n * n
      if (palindrome(square)) {
        rt = square :: rt
      }
    })
  }

  var tl = readLine
  if (tl != null) {
    val t = tl toInt

    for (i <- 0 until t) {
      val line = readLine
      val nm = line.split(" ")
      val n = nm(0) toLong
      val m = nm(1) toLong

      val count = (0 /: rt)((c, p) => {
        if (p >= n && p <= m) {
          c + 1
        } else {
          c
        }
      })

      println(s"Case #${i + 1}: $count")

    }
  }
}