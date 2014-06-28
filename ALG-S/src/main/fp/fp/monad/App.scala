package fp.monad

object App extends App {
  val id = Id("Hello, ") flatMap (a =>
    Id("monad!") flatMap (b =>
      Id(a + b)))

  println(id)

  sealed trait Validation[+E, +A]
  case class Failure[E](head: E, tail: Vector[E])
    extends Validation[E, Nothing]
  case class Success[A](a: A) extends Validation[Nothing, A]

  def validationApplicative[E]: Applicative[({ type f[x] = Validation[E, x] })#f] =
    new Applicative[({ type f[x] = Validation[E, x] })#f] {
      def unit[A](a: => A) = Success(a)
      //      override def map2[A, B, C](fa: Validation[E, A], fb: Validation[E, B])(f: (A, B) => C) =
      //        (fa, fb) match {
      //          case (Success(a), Success(b)) => Success(f(a, b))
      //          case (Failure(h1, t1), Failure(h2, t2)) =>
      //            Failure(h1, t1 ++ Vector(h2) ++ t2)
      //          case (e @ Failure(_, _), _) => e
      //          case (_, e @ Failure(_, _)) => e
      //        }

      override def apply[A, B](fab: Validation[E, A => B])(a: Validation[E, A]): Validation[E, B] =
        (fab, a) match {
          case (Success(f), Success(a)) => Success(f(a))
          case (Failure(h1, t1), Failure(h2, t2)) => Failure(h1, t1 ++ Vector(h2) ++ t2)
          case (e @ Failure(_, _), _) => e
          case (_, e @ Failure(_, _)) => e
        }
    }

}