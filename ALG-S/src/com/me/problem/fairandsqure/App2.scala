package com.me.problem.fairandsqure

import scala.collection.mutable.ListBuffer

object App2 extends App {

  def check(node: X, n: Int): Boolean = {
    var valid = true
    var tempNode = node
    val as = node.toList

    for (k <- 0 until n if valid) {
      val is = 0
      val ie = k
      var value = 0
      for (i <- is to ie) {
        value += (as(k - i) * as(i))
      }
      valid = value < 10
    }

    for (k <- n until 2 * n - 1 if valid) {
      val js = k - n + 1
      val je = k - js
      var value = 0
      for (j <- js to je) {
        value += as(j) * as(k - j)
      }
      valid = value < 10
    }
    valid
  }

  def dfs(x: X, n: Int, half: Int, even: Boolean, p: Int): (Boolean, List[X]) = {
    var valid = check(x, x.height)
    if (!valid) {
      (false, Nil)
    } else {
      if (p > n) {
        (true, List(x))
      } else {
        val lh = ListBuffer.empty[X]
        var checkpoint = if(even) half else half + 1
        if (p > checkpoint) {
          //only one possibility left
          val distance = if(even) (p - checkpoint - 1) else (p - checkpoint)
          var tempNode = x
          while (tempNode.height != checkpoint) {
            tempNode = tempNode.parent
          }

          var goback = 0
          while (goback < distance) {
            goback += 1
            tempNode = tempNode.parent
          }

          val y = new X(tempNode.value, p)
          if (y.value == 0) {
            x set0 y
          } else if (y.value == 1) {
            x set1 y
          } else if (y.value == 2) {
            x set2 y
          }
          val (v, l) = dfs(y, n, half, even, p + 1)
          valid = v
          if (!valid) {
            x.set0(null)
            x.set1(null)
            x.set2(null)
          } else {
            lh ++= l
          }
        } else {
          x.set0(new X(0, p))
          val (v0, l0) = dfs(x._0, n, half, even, p + 1)
          var validCnt = 0
          if (!v0) {
            validCnt += 1
            x.set0(null)
          } else {
            lh ++= l0
          }
          x.set1(new X(1, p))
          val (v1, l1) = dfs(x._1, n, half, even, p + 1)
          if (!v1) {
            validCnt += 1
            x.set1(null)
          } else {
            lh ++= l1
          }
          x.set2(new X(2, p))
          val (v2, l2) = dfs(x._2, n, half, even, p + 1)
          if (!v2) {
            validCnt += 1
            x.set2(null)
          } else {
            lh ++= l2
          }
          if (validCnt == 3) {
            valid = false
          } else {
            valid = true
          }
        }
        (valid, lh.toList)
      }

    }
  }
  

  def func(d: Int): List[List[Int]] = {
    if (d == 1) {
      List(List(1), List(2), List(3))
    } else {
      val half = d / 2
      val even = d % 2 == 0
      val dhs = ListBuffer.empty[List[Int]]

      val (v1, l1) = dfs(new X(1, 1), d, half, even, 2)

      if (v1) {
        l1.foreach(x => {
          dhs += x.toList
        })
      }

      val (v2, l2) = dfs(new X(2, 1), d, half, even, 2)

      if (v2) {
        l2.foreach(x => {
          dhs += x.toList
        })
      }
      dhs.toList
    }
  }

  def sqaure(as: List[Int]): String = {
    val n = as.size
    
    val bs = ListBuffer.empty[Int]
    
    for (k <- 0 until n) {
      val is = 0
      val ie = k
      var value = 0
      for (i <- is to ie) {
        value += (as(k - i) * as(i))
      }
      bs += value
    }

    for (k <- n until 2 * n - 1) {
      val js = k - n + 1
      val je = k - js
      var value = 0
      for (j <- js to je) {
        value += as(j) * as(k - j)
      }
      bs += value
    }
    bs.mkString("")
  }
  
  val got = ListBuffer.empty[String]
//  val got = func(10)
//  got foreach (l => println(l.mkString("")))
  for(i <- 1 until 51) {
    val rt = func(i)
    rt.foreach(lx => {
      got += sqaure(lx)
    })
  }
  
  got foreach println
  
  //if -1 for s1 < s2, 0 for s1 == s2, 1 for s1 > s2
  def compareStr(s1: String, s2: String): Int = {
    if(s1.length() < s2.length()) {
      -1
    } else if(s1.length() > s2.length()) {
      1
    } else {
      s1.compareTo(s2)
    }
  }
  
  var tl = readLine
  if (tl != null) {
    val t = tl toInt

    for (i <- 0 until t) {
      val line = readLine
      val nm = line.split(" ")
      val n = nm(0)
      val m = nm(1)

      val count = (0 /: got)((c, s) => {
        val n1 = compareStr(n, s)
        if(n1 > 0) {
          c
        } else {
          val m1 = compareStr(s, m)
          if(m1 > 0) {
            c
          } else {
            c + 1
          }
        }
      })

      println(s"Case #${i + 1}: $count")

    }
  }
}

class X(val value: Int, val height: Int) {
  var _0: X = _
  def set0(x: X) = x match {
    case null => _0 = null
    case _ =>
      _0 = x
      x.parent = this
  }
  var _1: X = _
  def set1(x: X) = x match {
    case null => _1 = null
    case _ =>
      _1 = x
      x.parent = this
  }
  var _2: X = _
  def set2(x: X) = x match {
    case null => _2 = null
    case _ =>
      _2 = x
      x.parent = this
  }
  var parent: X = _

  def toList(): List[Int] = {
    if (parent != null) {
      value :: parent.toList
    } else {
      List(value)
    }
  }

  override def toString = s"$value"
}