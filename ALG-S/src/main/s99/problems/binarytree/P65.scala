package problems.binarytree

object P65 extends App {

  println(Node('a', Node('b', End, Node('c')), Node('d')).layoutBinaryTree2)

  println(Node('a', Node('b', End, Node('c')), Node('d')).layoutBinaryTree3)
  val tree = Tree.fromList(List('n', 'k', 'm', 'c', 'a', 'e', 'd', 'g', 'u', 'p', 'q'))
  println(tree.layoutBinaryTree3)

  println(tree.bounds)
  tree match {
    case Node(_, left, right) => 
      println(left.bounds)
      println(right.bounds)
    case _ =>
  }
  
  val tree1 = Node('n', Node('k', Node('c', Node('a', End, End), Node('e', Node('d', End, End), Node('g', End, End))), End), Node('u', Node('p', End, Node('q', End, End)), End))
  println(tree1.layoutBinaryTree3)
}