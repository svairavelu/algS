package com.me.problems.leetcode

class LRUCacheS(val capacity: Int) {

  case class Node(val key: Int, var value: Int)
  
  var cache: List[Node] = Nil
  
  def set(key: Int, value: Int): Unit = Node(key, value) :: cache
  
  def get(key: Int): Int = {
    def getAndTrunc(nodes: List[Node], list: List[Node]): (List[Node], Int) = {
      if(nodes.length == capacity) {
        (nodes, -1)
      } else {
        val h = list.head
        if(h.key == key) {
          (h :: nodes ::: list.tail, h.value)
        } else {
          getAndTrunc(nodes ::: List(h), list.tail)
        }
      }
    }
    
    val (nodes, value) = getAndTrunc(Nil, cache)
    cache = nodes
    value
  }
}