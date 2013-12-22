package problems.binarytree

object P67 extends App {

  println(Node('a', Node('b', Node('d'), Node('e')), Node('c', End, Node('f', Node('g'), End))).toString)
  
  println(Tree.fromString("a(b(d,e),c(,f(g,)))"))
}