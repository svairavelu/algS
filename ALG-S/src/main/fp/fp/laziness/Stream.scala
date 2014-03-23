package fp.laziness

trait Stream[+A] {
  import Stream._
  def uncons: Option[(A, Stream[A])]
  def isEmpty: Boolean = uncons.isEmpty

  def toList: List[A] = uncons.fold(Nil: List[A]) {
    case (a, tail) => a :: tail.toList
  }

  def toList1: List[A] = {
    @annotation.tailrec
    def go(s: Stream[A], acc: List[A]): List[A] = s.uncons match {
      case Some((a, sa)) =>
        go(sa, a :: acc)
      case _ => acc.reverse
    }
    go(this, List())
  }

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

  def takeWhileWithFoldRight(p: A => Boolean): Stream[A] =
    foldRight(empty: Stream[A])((a, b) => if (p(a)) cons(a, b) else empty)
  def map[B](f: A => B): Stream[B] =
    foldRight[Stream[B]](empty)((a, bf) => cons(f(a), bf))

  def filter(p: A => Boolean): Stream[A] = foldRight(empty[A])((a, t) => if (p(a)) cons(a, t) else t)

  def append[B >: A](s: Stream[B]): Stream[B] = foldRight(s)((a, t) => cons(a, t))

  def flatMap[B](f: A => Stream[B]): Stream[B] = foldRight(empty[B])((a, t) => f(a) append t)

  def mapWithUnfold[B](f: A => B): Stream[B] = unfold(this)(slf => {
    slf.uncons match {
      case None => None
      case Some((h, t)) => Some(f(h), t)
    }
  })

  def takeWithUnfold(n: Int): Stream[A] = unfold((n, this)) {
    case (x, slf) if x == 0 => None
    case (x, slf) => slf.uncons match {
      case None => None
      case Some((a, t)) => Some(a, (x - 1, t))
    }
  }

  def takeWhileWithUnfold(p: A => Boolean): Stream[A] = unfold(this)(slf => slf.uncons match {
    case Some((a, t)) if (p(a)) => Some(a, t)
    case _ => None
  })

  def zip[B](bs: Stream[B]): Stream[(A, B)] = unfold((this, bs)) {
    case (as, bs) => (as.uncons, bs.uncons) match {
      case (_, None) => None
      case (None, _) => None
      case (Some((a, ta)), Some((b, tb))) => Some((a, b), (ta, tb))
    }
  }

  def zipAll[B](s2: Stream[B]): Stream[(Option[A], Option[B])] = unfold(this.uncons, s2.uncons) {
    case (None, None) => None
    case (None, Some((b, bt))) => Some((None, Some(b)), (None, bt.uncons))
    case (Some((a, at)), None) => Some((Some(a), None), (at.uncons, None))
    case (Some((a, at)), Some((b, bt))) => Some((Some(a), Some(b)), (at.uncons, bt.uncons))
  }

  def tails: Stream[Stream[A]] = unfold(this)(slf => slf.uncons.fold(None: Option[(Stream[A], Stream[A])]) {
    case (h, tail) => Some(slf, tail)
  }) append (Stream(empty))

  //  def scanRight[B](z: B)(f: (A, => B) => B): Stream[B] = tails.map(as => as.foldRight(z)(f))
  def scanRight[B](z: B)(f: (A, => B) => B): Stream[B] =
    foldRight((z, Stream(z)))((a, p) => {
      val b2 = f(a, p._1)
      (b2, cons(b2, p._2))
    })._2
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

  def constant[A](a: A): Stream[A] = cons(a, constant(a))

  def from(n: Int): Stream[Int] = cons(n, from(n + 1))

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case None => empty
    case Some((a, b)) => cons(a, unfold(b)(f))
  }

  def constant1[A](a: A) = unfold(a)(x => Some(x, x))

  def from1(n: Int) = unfold(n)(x => Some(x, x + 1))

  def startsWith[A](s1: Stream[A], s2: Stream[A]): Boolean =
    s1.zipAll(s2).takeWhile(!_._2.isEmpty) forAll {
      case (Some(h), Some(h2)) if h == h2 => true
      case _ => false
    }

  def hasSubsequence[A](s1: Stream[A], s2: Stream[A]): Boolean =
    s1.tails exists (startsWith(_, s2))
}