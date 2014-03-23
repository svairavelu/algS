package fp.parallelism

import java.util.concurrent._

object Par {
  type Par[A] = ExecutorService => Future[A]

  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone = true
    def get(timeout: Long, units: TimeUnit) = get
    def isCancelled = false
    def cancel(evenIfRunning: Boolean): Boolean = false
  }

  def unit[A](a: A): Par[A] = es => UnitFuture(a)
  def async[A](a: => A): Par[A] = fork(unit(a))

  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  def fork_simple[A](a: => Par[A]): Par[A] =
    es => es.submit(new Callable[A] {
      def call = a(es).get
    })

  def asyncF[A, B](f: A => B): A => Par[B] = a => async(f(a))

  def product[A, B](fa: Par[A], fb: Par[B]): Par[(A, B)] =
    es => {
      val af = fa(es)
      val bf = fb(es)
      UnitFuture((af.get(), bf.get()))
    }

  def map[A, B](fa: Par[A])(f: A => B): Par[B] =
    es => UnitFuture(f(fa(es).get()))

  def map2[A, B, C](pa: Par[A], pb: Par[B])(f: (A, B) => C): Par[C] =
    map(product(pa, pb))(x => f(x._1, x._2))

  def sequence_simple[A](l: List[Par[A]]): Par[List[A]] =
    l.foldRight[Par[List[A]]](unit(List()))((h, t) => map2(h, t)(_ :: _))

  // This implementation forks the recursive step off to a new logical thread,
  // making it effectively tail-recursive. However, we are constructing
  // a right-nested parallel program, and we can get better performance by
  // dividing the list in half, and running both halves in parallel.
  // See `sequenceBalanced` below.
  def sequenceRight[A](as: List[Par[A]]): Par[List[A]] =
    as match {
      case Nil => unit(Nil)
      case h :: t => map2(h, fork(sequence(t)))(_ :: _)
    }

  // We define `sequenceBalanced` using `IndexedSeq`, which provides an
  // efficient function for splitting the sequence in half.
  def sequenceBalanced[A](as: IndexedSeq[Par[A]]): Par[IndexedSeq[A]] = fork {
    if (as.isEmpty) unit(Vector())
    else if (as.length == 1) map(as.head)(a => Vector(a))
    else {
      val (l, r) = as.splitAt(as.length / 2)
      map2(sequenceBalanced(l), sequenceBalanced(r))(_ ++ _)
    }
  }

  def sequence[A](as: List[Par[A]]): Par[List[A]] =
    map(sequenceBalanced(as.toIndexedSeq))(_.toList)

  def parMap[A, B](l: List[A])(f: A => B): Par[List[B]] = fork {
    val fbs: List[Par[B]] = l.map(asyncF(f))
    sequence(fbs)
  }

  def myParFilter[A](l: List[A])(f: A => Boolean): Par[List[A]] =
    l match {
      case Nil => unit(Nil)
      case h :: t if (f(h)) => map2(unit(h), myParFilter(t)(f))(_ :: _)
      case h :: t => myParFilter(t)(f)
    }

  def parFilter[A](l: List[A])(f: A => Boolean): Par[List[A]] = {
    val pars: List[Par[List[A]]] =
      l map (asyncF((a: A) => if (f(a)) List(a) else List()))
    map(sequence(pars))(_.flatten) // convenience method on `List` for concatenating a list of lists
  }

  def equal[A](e: ExecutorService)(p: Par[A], p2: Par[A]): Boolean =
    p(e).get == p2(e).get

  def delay[A](fa: => Par[A]): Par[A] =
    es => fa(es)

  def fork[A](a: => Par[A]): Par[A] =
    es => es.submit(new Callable[A] {
      def call = a(es).get
    })

  def choice[A](cond: Par[Boolean])(t: Par[A], f: Par[A]): Par[A] =
    es =>
      if (run(es)(cond).get) t(es) // Notice we are blocking on the result of `cond`.
      else f(es)

  def choiceN[A](a: Par[Int])(choices: List[Par[A]]): Par[A] =
    es => {
      val idx = run(es)(a).get()
      run(es)(choices(idx))
    }

  def choiceViaChoiceN[A](cond: Par[Boolean])(t: Par[A], f: Par[A]): Par[A] =
    choiceN(map(cond)(x => if (x) 0 else 1))(List(t, f))

  def choiceMap[A, B](a: Par[A])(choices: Map[A, Par[B]]): Par[B] =
    es => {
      val key = run(es)(a).get
      run(es)(choices(key))
    }

  def chooser[A, B](a: Par[A])(choices: A => Par[B]): Par[B] =
    es => run(es)(choices(run(es)(a).get))

  def choiceNViaChooser[A](a: Par[Int])(choices: List[Par[A]]): Par[A] =
    chooser(a)(choices)

  def choiceViaChooser[A](cond: Par[Boolean])(t: Par[A], f: Par[A]): Par[A] =
    chooser(cond)(x => if (x) t else f)

  def flatMap[A, B](a: Par[A])(f: A => Par[B]): Par[B] =
    es => run(es)(f(run(es)(a).get()))

  def join[A](a: Par[Par[A]]): Par[A] = flatMap(a)(identity)
}