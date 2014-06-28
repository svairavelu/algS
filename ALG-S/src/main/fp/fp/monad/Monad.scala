package fp.monad

import scala.language.higherKinds
import fp.scalacheck.Gen

trait Monad[M[_]] extends Applicative[M] {
  def unit[A](a: => A): M[A]
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  //  override def map[A, B](ma: M[A])(f: A => B): M[B] =
  //    flatMap(ma)(a => unit(f(a)))
  //  override def map2[A, B, C](ma: M[A], mb: M[B])(f: (A, B) => C): M[C] =
  //    flatMap(ma)(a => map(mb)(b => f(a, b)))

  def sequence[A](lma: List[M[A]]): M[List[A]] = lma match {
    case Nil => unit(Nil)
    case h :: tail => map2(h, sequence(tail))(_ :: _)
  }

  def traverse[A, B](la: List[A])(f: A => M[B]): M[List[B]] = la match {
    case Nil => unit(Nil)
    case h :: tail => map2(f(h), traverse(tail)(f))(_ :: _)
  }

  def replicateM[A](n: Int, ma: M[A]): M[List[A]] = sequence(List.fill(n)(ma))

  override def factor[A, B](ma: M[A], mb: M[B]): M[(A, B)] = map2(ma, mb)((_, _))

  def cofactor[A, B](e: Either[M[A], M[B]]): M[Either[A, B]] =
    e match {
      case Left(ma) => map(ma)(a => Left(a))
      case Right(mb) => map(mb)(b => Right(b))
    }

  def compose[A, B, C](f: A => M[B], g: B => M[C]): A => M[C] =
    a => flatMap(f(a))(g)

  def join[A](mma: M[M[A]]): M[A] = flatMap(mma)(identity)

  override def apply[A, B](mf: M[A => B])(ma: M[A]): M[B] =
    flatMap(mf)(f => map(ma)(a => f(a)))

}

object Monad {
  val genMonad = new Monad[Gen] {
    def unit[A](a: => A): Gen[A] = Gen.unit(a)
    def flatMap[A, B](ma: Gen[A])(f: A => Gen[B]): Gen[B] =
      ma flatMap f
  }

  val idMonad = new Monad[Id] {
    def unit[A](a: => A): Id[A] = Id(a)
    def flatMap[A, B](ma: Id[A])(f: A => Id[B]) = ma flatMap f
  }

  def eitherMonad[E] = new Monad[({ type f[x] = Either[E, x] })#f] {
    def unit[A](a: => A) = Right(a)
    def flatMap[A, B](ma: Either[E, A])(f: A => Either[E, B]) = ma match {
      case Left(e) => Left(e)
      case Right(a) => f(a)
    }
  }
}

case class Id[A](value: A) {
  def flatMap[B](f: A => Id[B]): Id[B] = f(value)
}

case class Reader[R, A](run: R => A)
object Reader {

  def readerMonad[R] = new Monad[({ type f[x] = Reader[R, x] })#f] {
    def unit[A](a: => A): Reader[R, A] = Reader(r => a)
    def flatMap[A, B](st: Reader[R, A])(f: A => Reader[R, B]): Reader[R, B] = Reader(r => f(st.run(r)).run(r))
  }
}
