package com.me.problems.leetcode

import scala.collection.immutable.Queue

object TreeNodeS extends App {

  class TreeNode[+T]

  case object End extends TreeNode[Nothing]
  case class Node[+T](val v: T, val l: Tree[T], val r: Tree[T]) extends TreeNode[T]
  

}