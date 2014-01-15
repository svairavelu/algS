package fp.laziness

trait Stream[+A] {
  import Stream._
  def uncons: Option[(A, Stream[A])]
  def isEmpty: Boolean = uncons.isEmpty

  def toList: List[A] = uncons.fold(Nil: List[A]) {
    case (a, tail) => a :: tail.toList
  }

  //  def toList: List[A] = {
  //    @annotation.tailrec
  //    def go(s: Stream[A], acc: List[A]): List[A] = s uncons match {
  //      case Some(c) =>
  //        go(c.tail, c.head :: acc)
  //      case _ => acc
  //    }
  //    go(this, List()).reverse
  //  }

  def take(n: Int): Stream[A] = if (n == 0) empty else {
    uncons.fold[Stream[A]](empty) {
      case (h, tail) => cons(h, tail.take(n - 1))
    }
  }

  def takeWhile(p: A => Boolean): Stream[A] = uncons.fold[Stream[A]](empty) {
    case (h, tail) if p(h) => cons(h, tail.takeWhile(p))
    case (_, tail) => tail.takeWhile(p)
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B =
    uncons match {
      case Some((h, t)) => f(h, t.foldRight(z)(f))
      case None => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

  def forAll(p: A => Boolean): Boolean = foldRight(true)((a, b) => p(a) && b)

  def map[B](f: A => B): Stream[B] =
    foldRight[Stream[B]](empty)((a, bf) => cons(f(a), bf))

  def filter(p: A => Boolean): Stream[A] = foldRight(empty[A])((a, t) => if (p(a)) cons(a, t) else t)

  def append[B >: A](s: Stream[B]): Stream[B] = foldRight(s)((a, t) => cons(a, t))

  def flatMap[B](f: A => Stream[B]): Stream[B] = foldRight(empty[B])((a, t) => f(a) append t)
}
object Stream {
  def empty[A]: Stream[A] =
    new Stream[A] { def uncons = None }
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
    new Stream[A] {
      lazy val uncons = Some((hd, tl))
    }
  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))
}