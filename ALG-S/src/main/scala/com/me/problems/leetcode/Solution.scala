package com.me.problems.leetcode

object Solution extends App {

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

  def reverseBetween[T](list: List[T], m: Int, n: Int): List[T] = {
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

  def jump(xs: List[Int]): Int = {
    //    case Nil => 0
    //    case x :: tail if x >= tail.length => 1
    //    case x :: tail => 
    //      var i = 1
    //      var min = xs.length
    //      
    //      var list = tail
    //      while(i <= x) {
    //        val num = 1 + jump(list)
    //        if(min > num) {
    //          min = num
    //        }
    //        list = list.tail
    //        i += 1
    //      }
    //      min

    def fr(idx: Int, num: Int, cur: Int, last: Int, xs: List[Int]): Int = xs match {
      case Nil => num
      case x :: tail =>
        if (idx > last) {
          fr(idx + 1, num + 1, math.max(cur, idx + x), cur, tail)
        } else {
          fr(idx + 1, num, math.max(cur, idx + x), last, tail)
        }
    }
    fr(0, 0, 0, 0, xs)
  }

  println(jump(List(5, 6, 4, 4, 6, 9, 4, 4, 7, 4,
    4, 8, 2, 6, 8, 1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6, 8,
    8, 4, 4, 2, 0, 3, 8, 5)))

  def searchRange(xs: Array[Int], target: Int): Array[Int] = {
    def f(s: Int, e: Int): Option[(Int, Int)] = (xs(s), xs(e)) match {
      case (`target`, `target`) => Some((s, e))
      case (_, _) if s == e => None
      case (x, y) =>
        val mid = (e - s) / 2 + s
        if (xs(mid) < target) {
          f(mid + 1, e)
        } else if (xs(mid) > target) {
          f(s, mid)
        } else {
          f(s, mid).map {
            case (ls, _) =>
              val rse = f(mid + 1, e)
              rse match {
                case Some((_, re)) => (ls, re)
                case None => (ls, mid)
              }
          }
        }

    }

    (f(0, xs.length - 1).map {
      case (s, e) =>
        val nxs: Array[Int] = new Array(e - s + 1)
        Array.copy(xs, s, nxs, 0, e - s + 1)
        nxs
    }).getOrElse(null)
  }

  println(searchRange(Array(5, 7, 7, 8, 8, 10), 8).mkString(" "))

  def longestValidParentheses(str: String): Int = {
    def mergeRanges(ranges: List[(Int, Int)]): List[(Int, Int)] = ranges match {
      case x :: y :: tail =>
        val (xs, xe) = x
        val (ys, ye) = y
        if (xs < ys) {
          mergeRanges((xs, xe) :: tail)
        } else if (ye + 1 == xs) {
          (ys, xe) :: tail
        } else {
          ranges
        }
      case _ => ranges
    }

    def fun(ranges: List[(Int, Int)], ps: List[Int], idx: Int): List[(Int, Int)] = {
      if (idx == str.length) ranges
      else {
        str(idx) match {
          case '(' => fun(ranges, idx :: ps, idx + 1)
          case ')' =>
            ps match {
              case Nil => fun(ranges, ps, idx + 1)
              case sIdx :: tail =>
                fun(mergeRanges((sIdx, idx) :: ranges), tail, idx + 1)
            }
        }
      }
    }

    val ranges = fun(Nil, Nil, 0)

    ranges.foldLeft(0) {
      case (max, (s, e)) =>
        val n = e - s + 1
        if (n > max) n else max
    }
  }

  def fourSum(xs: List[Int], target: Int): List[(Int, Int, Int, Int)] = {
    if (xs.length < 4) Nil
    else {
      var ys = xs.sorted

      val head = ys.head
      var t = target
      if (head < 0) {
        ys = ys.map(x => x - head)
        t = target - 4 * head
      }

      ys = ys.dropWhile(_ > target)

      val zs = ys.toArray

      def fun1(list: List[(Int, Int)], s: Int, e: Int, t: Int): Option[List[(Int, Int)]] = (zs(s), zs(e)) match {
        case (_, _) if (s >= e) => if (list.isEmpty) None else Some(list)
        case (x, y) if x + y == t => fun1((s, e) :: list, s + 1, e - 1, t)
        case (x, y) if (x + y < t) =>
          fun1(list, s + 1, e, t)
        case (x, y) => fun1(list, s, e - 1, t)
      }

      def fun(s: Int, e: Int): Option[List[(Int, Int, Int, Int)]] = (zs(s), zs(e)) match {
        case (x, y) if (x + y >= t) => None
        case (x, y) =>
          fun1(Nil, s + 1, e - 1, t - (x + y)).map {
            case list =>
              list.map {
                case (s1, e1) => (s, s1, e1, e)
              }
          }
      }

      var i = 0;
      var j = zs.length - 1
      var rt: List[(Int, Int, Int, Int)] = Nil
      while (i < j - 2) {
        fun(i, j).map {
          list =>
            rt :::= (list.map {
              case (a, b, c, d) => (zs(a) + head, zs(b) + head, zs(c) + head, zs(d) + head)
            })
        }

        if (zs(i) + zs(i + 1) + zs(i + 2) + zs(j) > t) {
          j -= 1;
        } else if (zs(i) + zs(j - 2) + zs(j - 1) + zs(j) < t) {
          i += 1;
        } else {
          i += 1;
          j -= 1;
        }
      }
      rt
    }
  }

  val fourSumTestRt = println(fourSum(List(-3, -1, 0, 2, 4, 5), 2))

  //  println(longestValidParentheses("(()"))
  //  println(longestValidParentheses("(()(((()"))
  //  println(longestValidParentheses(")()((()))"))

  //  println(longestValidParentheses(scala.io.Source.fromFile("src/main/scala/com/me/problems/leetcode/longestValidParenthesesTest.txt").mkString))

  trait TreeNode[+T] {
  }

  case class Node[+T](val value: T, val left: TreeNode[T], val right: TreeNode[T]) extends TreeNode[T] {
  }

  object End extends TreeNode[Nothing] {
    override def toString = "#"
  }

  object TreeNode {
    private def postOrderInternal[U](trees: List[TreeNode[U]], values: Vector[U]): Vector[U] = trees match {
      case Nil => values
      case Node(value, End, End) :: tail =>
        postOrderInternal(tail, values :+ value)
      case Node(value, End, right) :: tail =>
        postOrderInternal(right :: Node(value, End, End) :: tail, values)
      case Node(value, left, right) :: tail =>
        postOrderInternal(left :: Node(value, End, right) :: tail, values)
      case _ => throw new IllegalArgumentException("shoud not go here")
    }

    def postorderTraversal[T](tree: TreeNode[T]): Vector[T] = postOrderInternal(List(tree), Vector.empty[T])
  }

  val tree = Node(1, Node(2, End, End), End)
  println(TreeNode.postorderTraversal(tree))

  def wordBreak(str: String, dict: Set[String]): List[String] = {
    def findValidStop(idx: Int): List[Int] = 
      (for(i <- str.length until idx by -1 if dict.contains(str.substring(idx, i))) yield i).toList
    
    val indexArray = (for(i <- 0 until str.length) yield findValidStop(i)).toArray
    
    def collect(idx: Int, path: String, paths: List[String]): List[String] = {
      val stops = indexArray(idx)
      var npaths = paths	  
      for(stop <- stops) {
        var s = str.substring(idx, stop)
        if(idx > 0) {
          s = path + " " + s
        }
        if(stop == str.length()) {
          npaths = npaths :+ s
        } else {
          npaths = collect(stop, s, npaths)
        }
      }
      
      npaths
    }
    
    collect(0, "", Nil)
  }

  println(wordBreak("catsanddog", Set("cat", "cats", "and", "sand", "dog")))
}