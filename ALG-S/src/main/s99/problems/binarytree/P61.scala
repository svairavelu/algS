package problems.binarytree

object P61 extends App {

  println(Node('x', Node('x'), End).leafCount)
  
  println(Node('a', Node('b'), Node('c', Node('d'), Node('e'))).leafList)
  
  println(Node('a', Node('b'), Node('c', Node('d'), Node('e'))).internalList)
  
  println(Node('a', Node('b'), Node('c', Node('d'), Node('e'))).atLevel(2))
}