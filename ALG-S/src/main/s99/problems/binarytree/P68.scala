package problems.binarytree

object P68 extends App {

  val tree = Tree.fromString("a(b(d,e),c(,f(g,)))")
  
  println(tree.preorder)
  
  println(tree.inorder)
  
  println(Tree.preInTree(List('a', 'b', 'd', 'e', 'c', 'f', 'g'), List('d', 'b', 'e', 'a', 'c', 'g', 'f')))
  
  println(Tree.preInTree(List('a', 'b', 'a'), List('b', 'a', 'a')))
  
  println(Tree.fromString("a(b(d,e),c(,f(g,)))").toDotstring)
  
  println(Tree.fromDotstring("abd..e..c.fg...").toDotstring)
}