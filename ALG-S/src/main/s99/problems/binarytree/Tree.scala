package problems.binarytree

sealed abstract class Tree[+T] {
  def isMirrorOf[V](that: Tree[V]): Boolean
  def isSymmetric: Boolean
  def addValue[U >: T <% Ordered[U]](x: U): Tree[U] = this match {
    case End => Node(x)
    case Node(v, left, right) if (x < v) => Node(v, left.addValue(x), right)
    case Node(v, left, right) => Node(v, left, right.addValue(x))
  }
}

case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def toString = "T(" + value.toString + " " + left.toString + " " + right.toString + ")"

  override def isMirrorOf[V](tree: Tree[V]): Boolean = tree match {
    case t: Node[V] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
    case _ => false
  }
  override def isSymmetric = left.isMirrorOf(right)
}

case object End extends Tree[Nothing] {
  override def toString = "."
  def isMirrorOf[V](tree: Tree[V]): Boolean = tree == End
  def isSymmetric = true
}

object Node {
  def apply[T](value: T): Node[T] = Node(value, End, End)
}

object Tree {
  def cBalanced[T](num: Int, v: T) = {
    def f(num: Int): List[Tree[T]] = num match {
      case 0 => List(End)
      case 1 => List(Node(v))
      case x if x % 2 == 0 =>
        val n0 = (x - 1) / 2
        val n1 = x - 1 - n0

        val tree0 = f(n0)
        val tree1 = f(n1)

        (for {
          l <- tree0
          r <- tree1
        } yield {
          new Node(v, l, r)
        }) ::: (
          for {
            r <- tree0
            l <- tree1
          } yield {
            new Node(v, l, r)
          })

      case x if x % 2 != 0 =>
        val left = f(x / 2)
        val right = f(x / 2)
        List(new Node(v, left.head, right.head))
    }

    f(num)
  }

  def fromList[T <% Ordered[T]](l: List[T]): Tree[T] =
    l.foldLeft(End: Tree[T])((r, e) => r.addValue(e))
}