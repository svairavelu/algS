package com.me.scalaz

object week1 extends App {

  import scalaz._
  import std.option._, std.list._ // functions and type class instances for Option and List
  import syntax.bind._

  List(true, false).ifM(List(1, 2), List(3, 4))

  class Container[A](value: A) { def addIt(implicit evidence: A =:= Int) = 123 + value }

  trait Plus[A] {
    def plus(a1: A, a2: A): A
  }

  def plus[A: Plus](a1: A, a2: A): A = implicitly[Plus[A]].plus(a1, a2)

  //  class IntPlus extends Plus[Int] {
  //    def plus(a1: Int, a2: Int): Int = a1 + a2
  //  }

  implicit val intPlus: Plus[Int] = new Plus[Int] {
    def plus(a1: Int, a2: Int): Int = a1 + a2
  }

  println(plus(1, 2))

  def sum(xs: List[Int]): Int = xs.foldLeft(0) { _ + _ }

  println(sum(List(1, 2, 3, 4)))

  trait Monoid[A] {
    def mappend(a1: A, a2: A): A
    def mzero: A
  }

  object IntMonoid extends Monoid[Int] {
    def mappend(a: Int, b: Int): Int = a + b
    def mzero: Int = 0
  }

  def sum1(xs: List[Int]): Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)

  println(sum1(List(1, 2, 3, 4)))

  def sum2(xs: List[Int], m: Monoid[Int]): Int = xs.foldLeft(m.mzero)(m.mappend)

  println(sum2(List(1, 2, 3, 4), IntMonoid))

  def sum3[A](xs: List[A], m: Monoid[A]): A = xs.foldLeft(m.mzero)(m.mappend)

  println(sum3(List(1, 2, 3, 4), IntMonoid))

  def sum4[A](xs: List[A])(implicit m: Monoid[A]): A = xs.foldLeft(m.mzero)(m.mappend)

  implicit val intMonoid = IntMonoid

  println(sum4(List(1, 2, 3, 4)))

  def sum5[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    xs.foldLeft(m.mzero)(m.mappend)
  }

  println(sum5(List(1, 2, 3, 4)))

  trait FoldLeft[F[_]] {
    def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
  }
  object FoldLeft {
    implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
      def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
    }
  }

  def sum6[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }

  implicit val strMonoid: Monoid[String] = new Monoid[String] {
    def mappend(a: String, b: String) = a + b
    def mzero = ""
  }
  println(sum6(List("a", "b", "c")))

  def plus1[A: Monoid](a: A, b: A): A = implicitly[Monoid[A]].mappend(a, b)

  println(plus1(3, 4))

  trait MonoidOp[A] {
    val F: Monoid[A]
    val value: A
    def |+|(a2: A) = F.mappend(value, a2)
  }

  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    val value = a
  }

  println(3 |+| 4)
  
  println("a" |+| "b")
  
}