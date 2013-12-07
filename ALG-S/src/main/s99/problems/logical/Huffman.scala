package problems.logical

class Node(val v: String, val freq: Int) {

  import Node._

  def this(x: (String, Int)) = this(x._1, x._2)

  var children = Map[Position, Node]()

  def +(that: Node): Node = {
    if (this.freq <= that.freq) {
      val nv = this.v + that.v
      val nf = this.freq + that.freq
      val parent = new Node(nv, nf)
      parent.children += (Left -> this)
      parent.children += (Right -> that)
      parent
    } else {
      that + this
    }
  }

  def contains(x: String) = v.contains(x)

  def encode(x: String, code: List[Int]): List[Int] = {
    if (x == v) {
      code.reverse
    } else {
      if (children(Left).contains(x)) {
        children(Left).encode(x, 0 :: code)
      } else {
        children(Right).encode(x, 1 :: code)
      }
    }
  }
}

object Node {
  trait Position
  case object Left extends Position
  case object Right extends Position
  def apply(v: String, f: Int) = new Node(v, f)
  def apply(x: (String, Int)) = new Node(x)
}

object P50 {
  def huffman(list: List[(String, Int)]) = {
    def f(list: List[Node]): List[Node] = {
      list match {
        case x :: y :: Nil => List(x + y)
        case x :: y :: tail =>
          val z = x + y
          f((z :: tail).sortBy(_.freq))
      }
    }

    val root = f(list.map(Node.apply).sortBy(_.freq)).head
    list.map {
      case (x, _) => (x, root.encode(x, Nil).mkString(""))
    }
  }

  def main(args: Array[String]) {
    println(huffman(List(("a", 45), ("b", 13), ("c", 12), ("d", 16), ("e", 9), ("f", 5))))
  }
}