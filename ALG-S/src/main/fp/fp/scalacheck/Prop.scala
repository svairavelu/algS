package fp.scalacheck

import fp.state.State
import fp.state.RNG
import scala.{ Stream => _ }
import fp.laziness.Stream

import Prop._
import Status._
import Gen._

case class Prop(run: (MaxSize, TestCases, RNG) => Result) {
  def &&(p: Prop) = Prop {
    (max, n, rng) =>
      run(max, n, rng) match {
        case Right((a, n)) => p.run(max, n, rng).right.map { case (s, m) => (s, n + m) }
        case l => l
      }
  }
  def ||(p: Prop) = Prop {
    (max, n, rng) =>
      run(max, n, rng) match {
        case Left(msg) => p.tag(msg).run(max, n, rng)
        case r => r
      }
  }
  /* This is rather simplistic - in the event of failure, we simply prepend
* the given message on a newline in front of the existing message.
*/
  def tag(msg: String) = Prop {
    (max, n, rng) =>
      run(max, n, rng) match {
        case Left(e) => Left(msg + "\n" + e)
        case r => r
      }
  }
}

object Prop {
  type TestCases = Int
  type MaxSize = Int
  type FailedCase = String
  type Result = Either[FailedCase, (Status, TestCases)]
  def forAll[A](a: Gen[A])(f: A => Boolean): Prop = Prop {
    (n, rng) =>
      {
        def go(i: Int, j: Int, s: Stream[Option[A]], onEnd: Int => Result): Result =
          if (i == j) Right((Unfalsified, i))
          else s.uncons match {
            case Some(c) => c.head match {
              case Some(h) =>
                try {
                  if (f(h)) go(i + 1, j, s, onEnd)
                  else Left(h.toString)
                } catch { case e: Exception => Left(buildMsg(h, e)) }
              case None => Right((Unfalsified, i))
            }
            case None => onEnd(i)
          }
        go(0, n / 3, a.exhaustive, i => Right((Proven, i))) match {
          case Right((Unfalsified, _)) =>
            val rands = randomStream(a)(rng).map(Some(_))
            go(n / 3, n, rands, i => Right((Unfalsified, i)))
          case s => s // If proven or failed, stop immediately
        }
      }
  }

  def apply(f: (TestCases, RNG) => Result): Prop =
    Prop { (_, n, rng) => f(n, rng) }

  def buildMsg[A](s: A, e: Exception): String =
    "test case: " + s + "\n" +
      "generated an exception: " + e.getMessage + "\n" +
      "stack trace:\n" + e.getStackTrace.mkString("\n")
}

sealed trait Status {}

object Status {
  case object Exhausted extends Status
  case object Proven extends Status
  case object Unfalsified extends Status
}

case class Gen[+A](sample: State[RNG, A], exhaustive: Stream[Option[A]]) {
  def map[B](f: A => B): Gen[B] =
    Gen(sample.map(f), exhaustive.map(_.map(f)))

  def map2[B, C](g: Gen[B])(f: (A, B) => C): Gen[C] =
    Gen(sample.map2(g.sample)(f), map2Stream(exhaustive, g.exhaustive)(map2Option(_, _)(f)))

  def flatMap[B](f: A => Gen[B]): Gen[B] =
    Gen(sample.flatMap(a => f(a).sample),
      exhaustive.flatMap {
        case None => unbounded
        case Some(a) => f(a).exhaustive
      })
  def listOfN(size: Int): Gen[List[A]] = Gen.listOfN(size, this)

  def listOfN(size: Gen[Int]): Gen[List[A]] = size flatMap (n => this.listOfN(n))
}

object Gen {
  //  type Gen[A] = State[RNG, A]

  //  def choose(start: Int, stopExclusive: Int): Gen[Int] =
  //    State(rng => {
  //      val (x, rng1) = RNG.positiveMax(stopExclusive - start)(rng)
  //      (x + start, rng1)
  //    })
  type Domain[+A] = Stream[Option[A]]

  def bounded[A](a: Stream[A]) = a map (Some(_))

  def unbounded: Domain[Nothing] = Stream(None)

  def unit[A](a: => A): Gen[A] = Gen(State.unit(a), bounded(Stream(a)))

  //  def boolean: Gen[Boolean] =
  //    Gen(State(rng => {
  //      val (x, rng1) = RNG.boolean(rng)
  //      (x, rng1)
  //    }), Stream.empty)

  //  def boolean: Gen[Boolean] = Gen(State.unit(true), Stream.constant(true))
  def boolean: Gen[Boolean] = Gen(State(RNG.boolean), bounded(Stream(true, false)))

  def choose(start: Int, stopExclusive: Int): Gen[Int] =
    Gen(State(rng => {
      val (x, rng1) = RNG.positiveMax(stopExclusive - start)(rng)
      (x + start, rng1)
    }), bounded(Stream.from(start).takeWhile(x => x < stopExclusive)))

  /* This implementation is rather tricky, but almost impossible to get wrong
* if you follow the types. It relies on several helper functions (see below).
*/
  def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] =
    Gen(State.sequence(List.fill(n)(g.sample)),
      cartesian(Stream.constant(g.exhaustive).take(n)).
        map(l => sequenceOption(l.toList)))

  /* `cartesian` generates all possible combinations of a `Stream[Stream[A]]`. For instance:
*
* cartesian(Stream(Stream(1,2), Stream(3), Stream(4,5))) ==
* Stream(Stream(1,3,4), Stream(1,3,5), Stream(2,3,4), Stream(2,3,5))
*/
  def cartesian[A](s: Stream[Stream[A]]): Stream[Stream[A]] =
    s.foldRight(Stream(Stream[A]()))((hs, ts) => map2Stream(hs, ts)(Stream.cons(_, _)))

  /* This is not the same as `zipWith`, a function we've implemented before.
* We are generating all (A,B) combinations and using each to produce a `C`.
* This implementation desugars to sa.flatMap(a => sb.map(b => f(a,b))).
*/
  def map2Stream[A, B, C](sa: Stream[A], sb: => Stream[B])(f: (A, => B) => C): Stream[C] =
    for { a <- sa; b <- sb } yield f(a, b)
  /* `map2Option` and `map2Stream`. Notice the duplication! */
  def map2Option[A, B, C](oa: Option[A], ob: Option[B])(f: (A, B) => C): Option[C] =
    for { a <- oa; b <- ob } yield f(a, b)

  /* This is a function we've implemented before. Unfortunately, it does not
* exist in the standard library. This implementation is uses a foldLeft,
* followed by a reverse, which is equivalent to a foldRight, but does not
* use any stack space.
*/
  def sequenceOption[A](o: List[Option[A]]): Option[List[A]] =
    o.foldLeft[Option[List[A]]](Some(List()))(
      (t, h) => map2Option(h, t)(_ :: _)).map(_.reverse)

  /* The simplest possible implementation. This will put all elements of one
* `Gen` before the other in the exhaustive traversal. It might be nice to
* interleave the two streams, so we get a more representative sample if we
* don't get to examine the entire exhaustive stream.
*/
  def union_1[A](g1: Gen[A], g2: Gen[A]): Gen[A] =
    boolean.flatMap(b => if (b) g1 else g2)

  def union[A](g1: Gen[A], g2: Gen[A]): Gen[A] =
    Gen(
      State(RNG.boolean).flatMap(b => if (b) g1.sample else g2.sample),
      interleave(g1.exhaustive, g2.exhaustive))

  def interleave[A](s1: Stream[A], s2: Stream[A]): Stream[A] =
    s1.zipAll(s2).flatMap { case (a, a2) => Stream((a.toList ++ a2.toList): _*) }

  /* Produce an infinite random stream from a `Gen` and a starting `RNG`. */
  def randomStream[A](g: Gen[A])(rng: RNG): Stream[A] =
    Stream.unfold(rng)(rng => Some(g.sample.run(rng)))

}