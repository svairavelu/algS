package problems.binarytree

object P57 extends App {

  val x = End.addValue(2)

  val y = x.addValue(3)

  val z = y.addValue(1)

  println(x)
  println(y)
  println(z)

  val w = Tree.fromList(List(5, 3, 18, 1, 4, 12, 21))
  
  println(w)
  
  println(s"is symmetric? ${w.isSymmetric}")
}