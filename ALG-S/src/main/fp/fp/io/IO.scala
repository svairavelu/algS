package fp.io

import fp.monad.Monad

import scala.language.higherKinds

trait IO[F[_], +A]
case class Pure[F[_], +A](get: A) extends IO[F, A]
case class Request[F[_], I, +A](
  expr: F[I],
  receive: I => IO[F, A]) extends IO[F, A]

trait Runnable[A] { def run: A }

object Delay { def apply[A](a: => A) = new Runnable[A] { def run = a } }

trait Console[A]
case object ReadLine extends Console[Option[String]]
case class PrintLine(s: String) extends Console[Unit]

trait Run[F[_]] {
  def apply[A](expr: F[A]): (A, Run[F])
}

object IO {
  @annotation.tailrec
  def run[F[_], A](R: Run[F])(io: IO[F, A]): A = io match {
    case Pure(a) => a
    case Request(expr, recv) =>
      R(expr) match { case (e, r2) => run(r2)(recv(e)) }
  }
}

object RunConsoleMock extends Run[Console] {
  def apply[A](c: Console[A]) = c match {
    case ReadLine => (Some("Hello world!"), RunConsoleMock)
    case PrintLine(_) => ((), RunConsoleMock)
  }
}

trait Trampoline[+A] { def run: A = Trampoline.run(this) }

// companion object itself is the Monad instance
object Trampoline extends Monad[Trampoline] {

  case class Done[+A](get: A) extends Trampoline[A]
  case class More[+A](force: () => Trampoline[A]) extends Trampoline[A]
  case class Bind[A, +B](force: () => Trampoline[A],
    f: A => Trampoline[B]) extends Trampoline[B]

  /*

Exercise 5: Implement a tail-recursive `run` function for evaluating
a `Trampoline[A]` to an `A`.
Exercise 6: Implement `Monad[Trampoline]`.

*/

  @annotation.tailrec
  def run[A](t: Trampoline[A]): A = t match {
    case Done(a) => a
    case More(k) => run(k())
    case Bind(force, f) => run(flatMap(force())(f))
  }

  def unit[A](a: => A) = Done(a)
  def flatMap[A, B](a: Trampoline[A])(f: A => Trampoline[B]): Trampoline[B] =
    a match {
      case Done(forced) => f(forced)
      case More(force) => Bind(force, f)
      case Bind(force, g) => More(() => Bind(force, g andThen (flatMap(_)(f))))
    }
  def more[A](a: => Trampoline[A]): Trampoline[A] =
    More(() => a)
  def delay[A](a: => A): Trampoline[A] =
    More(() => Done(a))
  def done[A](a: A): Trampoline[A] = Done(a)
}