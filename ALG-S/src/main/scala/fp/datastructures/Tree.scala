package fp.datastructures

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  def size[_](t: Tree[_]): Int = t match {
    case Leaf(_) => 1
    case Branch(left, right) => 1 + size(left) + size(right)
  }

  def maximum(t: Tree[Int]): Int = t match {
    case Leaf(x) => x
    case Branch(left, right) =>
      val x = maximum(left)
      val y = maximum(right)
      x max y
  }

  def depth[A](t: Tree[A]): Int = t match {
    case Leaf(_) => 1
    case Branch(left, right) =>
      val x = depth(left)
      val y = depth(right)
      (x max y) + 1
  }

  def map[A, B](t: Tree[A])(f: A => B): Tree[B] = t match {
    case Leaf(x) => Leaf(f(x))
    case Branch(left, right) =>
      Branch(map(left)(f), map(right)(f))
  }

  def fold[A, B](t: Tree[A])(f: A => B)(g: (B, B) => B): B = t match {
    case Leaf(a) => f(a)
    case Branch(left, right) => g(fold(left)(f)(g), fold(right)(f)(g))
  }

  def sizeWithFold[A](t: Tree[A]): Int = fold(t)(x => 1)((x, y) => 1 + x + y)

  def maximumWithFold(t: Tree[Int]): Int = fold(t)(identity)(_ max _)

  def depthWithFold[A](t: Tree[A]): Int = fold(t)(x => 1)((x, y) => (x max y) + 1)

  def mapWithFold[A, B](t: Tree[A])(f: A => B): Tree[B] = fold(t)(x => Leaf(f(x)): Tree[B])(Branch(_, _))
}