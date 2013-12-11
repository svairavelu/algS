package problems.binarytree

object P56 extends App {

  import Tree._
  
  println(Node('a', Node('b'), Node('c')).isSymmetric)
  
  println(Node('a', Node('b'), End).isSymmetric)
}