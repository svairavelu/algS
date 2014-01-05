package com.me.problems.leetcode

import scala.collection.mutable.Queue

abstract class Tree[+T] {

}

case object End extends Tree[Nothing]

case class Node[+T](val v: T, val l: Tree[T], val r: Tree[T]) extends Tree[T]

object Tree {
  def minHeight[T](tree: Tree[T]): Int = {
    var heights = Map.empty[Tree[T], Integer]
    var queue = Queue.empty[Tree[T]]
    queue.enqueue(tree)
    heights += tree -> 0
    var found = false
    var minHeight = 0
    while (!queue.isEmpty && !found) {
      val node = queue.dequeue

      node match {
        case End =>
        case Node(_, End, End) =>
          found = true
          minHeight = heights(node)
        case Node(_, l, r) =>
          heights += l -> (heights(node) + 1)
          heights += r -> (heights(node) + 1)
      }
    }

    minHeight
  }

  def minDepth[T](tree: Tree[T]): Int = tree match {
    case End => -1
    case Node(_, End, End) => 1
    case Node(_, l, r) => math.min(minDepth(l), minDepth(r)) + 1
  }
}