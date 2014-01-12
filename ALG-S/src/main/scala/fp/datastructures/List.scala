package fp.datastructures

sealed trait List[+A] {
  def tail: List[A]
}

case object Nil extends List[Nothing] {
  override def tail = Nil
}

case class Cons[+A](head: A, tail: List[A]) extends List[A] {
}

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, tail) => x + sum(tail)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, tail) => 0.0
    case Cons(x, ds) => x * product(ds)
  }

  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  }

  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nil => Nil
    case xs @ Cons(_, _) if n == 0 => xs
    case Cons(_, xs) if n == 1 => xs
    case Cons(_, xs) => drop(xs, n - 1)
  }

  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Cons(x, xs) if f(x) => dropWhile(xs)(f)
    case _ => l
  }

  def setHead[A](l: List[A])(h: A): List[A] = l match {
    case Nil => Cons(h, Nil)
    case Cons(_, tail) => Cons(h, tail)
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }

  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(x, Cons(y, Nil)) => Cons(x, Nil)
    case Cons(x, tail) => Cons(x, init(tail))
  }

  def foldRight[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    l match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

  def sum2(l: List[Int]) =
    foldRight(l, 0.0)(_ + _)
  def product2(l: List[Double]) =
    foldRight(l, 1.0)(_ * _)

  def length(l: List[_]): Int = foldRight(l, 0) {
    case (x, z) => z + 1
  }

  def foldLeft[A, B](l: List[A], z: B)(f: (B, A) => B): B = {
    def go(acc: B, xs: List[A]): B = xs match {
      case Nil => acc
      case Cons(x, xs) => go(f(acc, x), xs)
    }

    go(z, l)
  }

  def sum3(l: List[Int]) = foldLeft(l, 0)(_ + _)

  def product3(l: List[Double]) = foldLeft(l, 1.0)(_ * _)

  def length3(l: List[_]) = foldLeft(l, 0)((z, x) => z + 1)

  def reverse[A](l: List[A]): List[A] = foldLeft(l, Nil: List[A])((rl, x) => Cons(x, rl))

  /*
The implementation of `foldRight` in terms of `reverse` and `foldLeft` is a common trick for avoiding stack overflows when implementing a strict `foldRight` function as we've done in this chapter. (We will revisit this in a later chapter, when we discuss laziness).
The other implementations build up a chain of functions which, when called, results in the operations being performed with the correct associativity.
*/
  def foldRightViaFoldLeft[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(reverse(l), z)((b, a) => f(a, b))

  def foldRightViaFoldLeft_1[A, B](l: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(l, (b: B) => b)((g, a) => b => g(f(a, b)))(z)

  def foldLeftViaFoldRight[A, B](l: List[A], z: B)(f: (B, A) => B): B =
    foldRight(l, (b: B) => b)((a, g) => b => g(f(b, a)))(z)

  def concat[A](ll: List[List[A]]): List[A] = foldRight(ll, Nil: List[A])((as, z) => foldRight(as, z)((a, z) => Cons(a, z)))

  def concat1[A](ll: List[List[A]]): List[A] = foldRight(ll, Nil: List[A])(append)

  def addOne(l: List[Int]): List[Int] = l match {
    case Nil => Nil
    case Cons(x, tail) => Cons(x + 1, addOne(tail))
  }

  def dbListToSList(l: List[Double]): List[String] = l match {
    case Nil => Nil
    case Cons(d, tail) => Cons(d.toString, dbListToSList(tail))
  }

  def map[A, B](l: List[A])(f: A => B): List[B] = l match {
    case Nil => Nil
    case Cons(h, tail) => Cons(f(h), map(tail)(f))
  }

  def filter[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(x, tail) if (f(x)) => Cons(x, filter(tail)(f))
    case Cons(_, tail) => filter(tail)(f)
  }

  def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] = l match {
    case Nil => Nil
    case Cons(x, tail) => append(f(x), flatMap(tail)(f))
  }

  def filterWithFlatMap[A](l: List[A])(f: A => Boolean): List[A] = flatMap(l)(x => if (f(x)) Cons(x, Nil) else Nil)
  
  def addTwoLists(l1: List[Int], l2: List[Int]): List[Int] = (l1, l2) match {
    case (Nil, _) => Nil
    case (_, Nil) => Nil
    case (Cons(x, t1), Cons(y, t2)) => Cons(x + y, addTwoLists(t1, t2))
  }
  
  def hasSubsequence[A](l: List[A], sub: List[A]): Boolean = {
    def check(l: List[A], r: List[A]): Boolean = (l, r) match {
      case (_, Nil) => true
      case (Cons(x, lt), Cons(y, rt)) if(x == y) => check(lt, rt)
      case _ => false
    }
    
    def go(l: List[A]): Boolean = l match {
      case Nil => false
      case Cons(_, tail) if(check(l, sub)) => true
      case Cons(_, tail) => go(tail) 
    }
    
    go(l)
  }
}