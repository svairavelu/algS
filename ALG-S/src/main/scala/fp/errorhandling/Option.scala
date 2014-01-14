package fp.errorhandling

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }
  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case None => None
    case Some(a) => f(a)
  }
  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(x) => x
  }
  def orElse[B >: A](ob: => Option[B]): Option[B] = this match {
    case None => ob
    case Some(x) => this
  }
  def filter(f: A => Boolean): Option[A] = this match {
    case Some(x) if f(x) => this
    case _ => None
  }

  //  def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f
}
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def map2_1[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    a match {
      case None => None
      case Some(x) =>
        b match {
          case None => None
          case Some(y) => Some(f(x, y))
        }
    }
  }

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    a.flatMap(x => b.map(y => f(x, y)))
  }

  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
    def go(xs: List[A], as: List[Option[A]]): Option[List[A]] = as match {
      case Nil => Some(xs.reverse)
      case None :: tail => None
      case Some(x) :: tail => go(x :: xs, tail)
    }
    go(Nil, a)
  }

  def sequence_1[A](a: List[Option[A]]): Option[List[A]] =
    a match {
      case Nil => Some(Nil)
      case h :: t => h flatMap (hh => sequence_1(t) map (hh :: _))
    }

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    def go(xs: List[B], as: List[A]): Option[List[B]] = as match {
      case Nil => Some(xs.reverse)
      case a :: tail => f(a).flatMap(h => go(h :: xs, tail))
    }

    go(Nil, a)
  }

  def sequence_2[A](a: List[Option[A]]): Option[List[A]] = traverse(a)(identity)

  /*
It can also be implemented using `foldRight` and `map2`. The type annotation on `foldRight` is needed here, otherwise Scala wrongly infers the result type of the fold as `Some[Nil.type]` and reports a type error (try it!). This is an unfortunate consequence of Scala using subtyping to encode algebraic data types.
*/
  def sequence_3[A](a: List[Option[A]]): Option[List[A]] =
    a.foldRight(Some(Nil): Option[List[A]])((x, y) => map2(x, y)(_ :: _))

  def traverse_1[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a match {
      case Nil => Some(Nil)
      case h :: t => map2(f(h), traverse_1(t)(f))(_ :: _)
    }

  def traverse_2[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a.foldRight[Option[List[B]]](Some(Nil))((h, t) => map2(f(h), t)(_ :: _))
}