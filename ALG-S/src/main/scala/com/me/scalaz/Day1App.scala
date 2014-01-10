package com.me.scalaz

object Day1App extends App {

  import scalaz._
  import Scalaz._

  1 > 3.0

  1 gt 2

  println(1.0 ?|? 2.0)

  val rel = 1.0 ?|? 2.0

  1.0 max 2.0

  println('a' to 'e')

  val ae = 'a' |-> 'e'

  val x = 3 |=> 5

  println(x.head())

  trait CanTruthy[A] { self =>
    /** @return true, if `a` is truthy. */
    def truthys(a: A): Boolean
  }
  object CanTruthy {
    def apply[A](implicit ev: CanTruthy[A]): CanTruthy[A] = ev
    def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
      def truthys(a: A): Boolean = f(a)
    }
  }
  trait CanTruthyOps[A] {
    def self: A
    implicit def F: CanTruthy[A]
    final def truthy: Boolean = F.truthys(self)
  }

  object ToCanIsTruthyOps {
    import scala.language.implicitConversions
    implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
      new CanTruthyOps[A] {
        def self = v
        implicit def F: CanTruthy[A] = ev
      }

    implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
      case 0 => false
      case _ => true
    })

    implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
      case Nil => false
      case _ => true
    })

    implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)

    implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)

  }

  import ToCanIsTruthyOps._

  println(10.truthy)

  println(List(10).truthy)

  println(Nil.truthy)

  println(false.truthy)

  def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) =
    if (cond.truthy) ifyes
    else ifno

  println(truthyIf(Nil) { "YEAH!" } { "NO!" })

  println(truthyIf(2 :: 3 :: 4 :: Nil) { "YEAH!" } { "NO!" })
}