package fp.monoid

trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

object Monoid {
  val stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String) = a1 + a2
    def zero = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    def zero = Nil
  }

  val intAddition: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 + a2
    def zero = 0
  }
  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 * a2
    def zero = 1
  }
  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 || a2
    def zero = false
  }
  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 && a2
    def zero = true
  }

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def op(x: Option[A], y: Option[A]) = x orElse y
    def zero = None
  }

  def EndoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    def op(a1: A => A, a2: A => A) = a1 andThen a2
    def zero = a => a
  }

  def trimMonoid: Monoid[String] = new Monoid[String] {
    def op(a: String, b: String) = (a.trim + " " + b.trim).trim
    val zero = ""
  }

  def foldLeft[A](as: List[A], m: Monoid[A]): A = {
    def go(x: A, as: List[A]): A = as match {
      case Nil => x
      case h :: tail => go(m.op(x, h), tail)
    }
    go(m.zero, as)
  }

  def concatenate[A](as: List[A], m: Monoid[A]): A = foldLeft(as, m)

  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B = concatenate(as.map(f), m)

  def foldLeftViaFoldMap[A](as: List[A], m: Monoid[A]): A = foldMap(as, m)(identity)

  def foldMapV[A, B](as: IndexedSeq[A], m: Monoid[B])(f: A => B): B =
    if (as.length == 0)
      m.zero
    else if (as.length == 1)
      f(as(0))
    else {
      val (l, r) = as.splitAt(as.length / 2)
      m.op(foldMapV(l, m)(f), foldMapV(r, m)(f))
    }
  def ordered(ints: IndexedSeq[Int]): Boolean = {
    // Our monoid tracks the minimum and maximum element seen so far
    // as well as whether the elements are so far ordered.
    val mon = new Monoid[Option[(Int, Int, Boolean)]] {
      def op(o1: Option[(Int, Int, Boolean)], o2: Option[(Int, Int, Boolean)]) =
        (o1, o2) match {
          // The ranges should not overlap if the sequence is ordered.
          case (Some((x1, y1, p)), Some((x2, y2, q))) =>
            Some((x1 min x2, y1 max y2, p && q && y1 <= x2))
          case (x, None) => x
          case (None, x) => x
        }
      val zero = None
    }
    // The empty sequence is ordered, and each element by itself is ordered.
    foldMapV(ints, mon)(i => Some((i, i, true))).map(_._3).getOrElse(true)
  }

  def productMonoid[A, B](a: Monoid[A], b: Monoid[B]): Monoid[(A, B)] = new Monoid[(A, B)] {
    def zero = (a.zero, b.zero)
    def op(x: (A, B), y: (A, B)) = (a.op(x._1, y._1), b.op(x._2, y._2))
  }

  def coproductMonoid[A, B](a: Monoid[A], b: Monoid[B]): Monoid[Either[A, B]] = new Monoid[Either[A, B]] {
    def zero = Left(a.zero)
    def op(x: Either[A, B], y: Either[A, B]) =
      (x, y) match {
        case (Left(a), _) => Left(a)
        case (_, Left(b)) => Left(b)
        case (_, Right(b)) => Right(b)
      }
  }

  def functionMonoid[A, B](B: Monoid[B]): Monoid[A => B] = new Monoid[A => B] {
    def zero = _ => B.zero
    def op(x: A => B, y: A => B) =
      a => B.op(x(a), y(a))
  }

  def mapMergeMonoid[K, V](V: Monoid[V]): Monoid[Map[K, V]] =
    new Monoid[Map[K, V]] {
      def zero = Map()
      def op(a: Map[K, V], b: Map[K, V]) = {
        //        (a.map {
        //          case (k, v) => (k, V.op(v, b.get(k) getOrElse V.zero))
        //
        val c = for {
          (ka, va) <- a
          (kb, vb) <- b
          if (ka == kb)
        } yield (ka -> V.op(va, vb))

        a ++ b ++ c
      }
    }

  def bag[A](as: IndexedSeq[A]): Map[A, Int] =
    foldMapV(as, mapMergeMonoid[A, Int](intAddition))((a: A) => Map(a -> 1))
  def frequencyMap(strings: IndexedSeq[String]): Map[String, Int] = bag(strings.map(_.split("\\s+")).flatten)
}

sealed trait WC
case class Stub(chars: String) extends WC
case class Part(lStub: String, words: Int, rStub: String) extends WC

object WC {
  val wcMonoid: Monoid[WC] = new Monoid[WC] {
    def op(a: WC, b: WC) = (a, b) match {
      case (Stub(c), Stub(d)) => Stub(c + d)
      case (Stub(c), Part(l, w, r)) => Part(c + l, w, r)
      case (Part(l, w, r), Stub(c)) => Part(l, w, r + c)
      case (Part(l1, w1, r1), Part(l2, w2, r2)) =>
        Part(l1, w1 + (if ((r1 + l2).isEmpty) 0 else 1) + w2, r2)
    }
    def zero: WC = Stub("")
  }

  import Monoid._

  def count(s: String): Int = {
    // A single character's count. Whitespace does not count,
    // and non-whitespace starts a new Stub.
    def wc(c: Char): WC =
      if (c.isWhitespace)
        Part("", 0, "")
      else
        Stub(c.toString)
    // `unstub(s)` is 0 if `s` is empty, otherwise 1.
    def unstub(s: String) = s.length min 1
    foldMap(s.toList, wcMonoid)(wc) match {
      case Stub(s) => unstub(s)
      case Part(l, w, r) => unstub(l) + w + unstub(r)
    }
  }
}
