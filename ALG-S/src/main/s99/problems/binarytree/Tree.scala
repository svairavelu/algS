package problems.binarytree

sealed abstract class Tree[+T] {
  def isMirrorOf[V](that: Tree[V]): Boolean
  def isSymmetric: Boolean
  def addValue[U >: T <% Ordered[U]](x: U): Tree[U] = this match {
    case End => Node(x)
    case Node(v, left, right) if (x < v) => Node(v, left.addValue(x), right)
    case Node(v, left, right) => Node(v, left, right.addValue(x))
  }

  def height: Int
  def nodeCount: Int
  def leafCount: Int = this match {
    case End => 0
    case Node(_, End, End) => 1
    case Node(_, left, right) => left.leafCount + right.leafCount
  }
  
  def leafList: List[T] = this match {
    case End => List()
    case Node(v, End, End) => List(v)
    case Node(_, left, right) => left.leafList ::: right.leafList
  }
  
  def internalList: List[T] = this match {
    case End => Nil
    case Node(v, End, End) => Nil
    case Node(v, left, right) => v :: left.internalList ::: right.internalList
  }
  
  def atLevel(level: Int): List[T] = {
    def travel(n: Int, t: Tree[T]): List[T] = (n, t) match {
      case (`level`, Node(v, _, _)) => List(v)
      case (_, End) => List()
      case (_, Node(_, left, right)) =>
        travel(n + 1, left) ::: travel(n + 1, right)
    }
    
    travel(1, this)
  }
}

case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def toString = "T(" + value.toString + " " + left.toString + " " + right.toString + ")"

  override def isMirrorOf[V](tree: Tree[V]): Boolean = tree match {
    case t: Node[V] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
    case _ => false
  }

  override def height = 1 + math.max(left.height, right.height)

  override def isSymmetric = left.isMirrorOf(right)

  def nodeCount: Int = left.nodeCount + right.nodeCount + 1
}

case object End extends Tree[Nothing] {
  override def toString = "."
  def isMirrorOf[V](tree: Tree[V]): Boolean = tree == End
  def isSymmetric = true
  override def height = 0
  def nodeCount: Int = 0
}

object Node {
  def apply[T](value: T): Node[T] = Node(value, End, End)
}

object Tree {

  def cBalanced[T](nodes: Int, value: T): List[Tree[T]] = nodes match {
    case n if n < 1 => List(End)
    case n if n % 2 == 1 => {
      val subtrees = cBalanced(n / 2, value)
      subtrees.flatMap(l => subtrees.map(r => Node(value, l, r)))
    }
    case n if n % 2 == 0 => {
      val lesserSubtrees = cBalanced((n - 1) / 2, value)
      val greaterSubtrees = cBalanced((n - 1) / 2 + 1, value)
      lesserSubtrees.flatMap(l => greaterSubtrees.flatMap(g => List(Node(value, l, g), Node(value, g, l))))
    }
  }

  def symmetricBalancedTrees[T](num: Int, v: T) = cBalanced(num, v).filter(_.isSymmetric)

  def fromList[T <% Ordered[T]](l: List[T]): Tree[T] =
    l.foldLeft(End: Tree[T])((r, e) => r.addValue(e))

  def hbalTrees[T](height: Int, value: T): List[Tree[T]] = height match {
    case n if n < 1 => List(End)
    case 1 => List(Node(value))
    case _ => {
      val fullHeight = hbalTrees(height - 1, value)
      val short = hbalTrees(height - 2, value)
      fullHeight.flatMap((l) => fullHeight.map((r) => Node(value, l, r))) :::
        fullHeight.flatMap((f) => short.flatMap((s) => List(Node(value, f, s), Node(value, s, f))))
    }
  }

  def minHbalNodes(h: Int): Int = h match {
    case n if n < 1 => 0
    case 1 => 1
    case n => minHbalNodes(n - 1) + minHbalNodes(n - 2) + 1
  }

  def maxHbalNodes(height: Int): Int = 2 * height - 1

  def minHbalHeight(nodes: Int): Int =
    if (nodes == 0) 0
    else minHbalHeight(nodes / 2) + 1

  def maxHbalHeight(nodes: Int): Int =
    Stream.from(1).takeWhile(minHbalNodes(_) <= nodes).last

  def hbalTreesWithNodes[T](nodes: Int, value: T): List[Tree[T]] =
    (minHbalHeight(nodes) to maxHbalHeight(nodes)).flatMap(hbalTrees(_, value)).filter(_.nodeCount == nodes).toList
}