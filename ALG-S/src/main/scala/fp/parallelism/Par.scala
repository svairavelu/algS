package fp.parallelism

import java.util.concurrent._

object Par {
  type Par[A] = ExecutorService => Future[A]

  def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a) // `unit` is represented as a function that returns a `UnitFuture`, which is a simple implementation of `Future` that just wraps a constant value. It doesn't use the `ExecutorService` at all. It's always done and cannot be cancelled. Its `get` method simply returns the value that we gave it.

  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone = true
    def get(timeout: Long, units: TimeUnit) = get
    def isCancelled = false
    def cancel(evenIfRunning: Boolean): Boolean = false
  }

  def fork[A](a: => Par[A]): Par[A] = // This is the simplest and most natural implementation of `fork`, but there are some problems with it--for one, the outer `Callable` will block waiting for the "inner" task to complete. Since this blocking occupies a thread in our thread pool, or whatever resource backs the `ExecutorService`, this implies that we are losing out on some potential parallelism. Essentially, we are using two threads when one should suffice. This is a symptom of a more serious problem with the implementation, and we will discuss this later in the chapter.
    es => es.submit(new Callable[A] {
      def call = a(es).get
    })

  def async[A](a: => A): Par[A] = fork(unit(a))

  def delay[A](fa: => Par[A]): Par[A] =
    es => fa(es)

  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  def asyncF[A, B](f: A => B): A => Par[B] = a => async(f(a))

  //  def map[A, B](fa: Par[A])(f: A => B): Par[B] =
  //    map2(fa, unit(()))((a, _) => f(a))

  def product[A, B](fa: Par[A], fb: Par[B]): Par[(A, B)] = es => {
    val a = fa(es)
    val b = fb(es)
    UnitFuture(a.get, b.get)
  }
  def map[A, B](fa: Par[A])(f: A => B): Par[B] = es => {
    val a = fa(es)
    UnitFuture(f(a.get))
  }

  //   def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] = es => {
  //    val fa = fork(a)
  //    val fb = fork(b)
  //    val c = f(fa(es).get, fb(es).get)
  //    UnitFuture(c)
  //  }

  def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] = map(product(a, b)) {
    case (a, b) => f(a, b)
  }

  def sequence_simple[A](l: List[Par[A]]): Par[List[A]] =
    l.foldRight[Par[List[A]]](unit(List()))((h, t) => map2(h, t)(_ :: _))

  //  def sequence[A](l: List[Par[A]]): Par[List[A]] = es => {
  //    UnitFuture(l.map(pa => pa(es).get))
  //  }

  // This implementation forks the recursive step off to a new logical thread,
  // making it effectively tail-recursive. However, we are constructing
  // a right-nested parallel program, and we can get better performance by
  // dividing the list in half, and running both halves in parallel.
  // See `sequenceBalanced` below.
  def sequenceRight[A](as: List[Par[A]]): Par[List[A]] =
    as match {
      case Nil => unit(Nil)
      case h :: t => map2(h, fork(sequenceRight(t)))(_ :: _)
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

  def parMap[A, B](l: List[A])(f: A => B): Par[List[B]] = {
    val pl = l.map(asyncF(f))
    sequence(pl)
  }

  def parFilter[A](l: List[A])(f: A => Boolean): Par[List[A]] = {
    val pars: List[Par[List[A]]] =
      l map (asyncF((a: A) => if (f(a)) List(a) else List()))
    map(sequence(pars))(_.flatten) // convenience method on `List` for concatenating a list of lists
  }

  def sum(as: IndexedSeq[Int]): Par[Int] =
    if (as.size <= 1) Par.unit(as.headOption getOrElse 0)
    else {
      val (l, r) = as.splitAt(as.length / 2)
      Par.map2(sum(l), sum(r))(_ + _)
    }

  def parRun[A](as: IndexedSeq[A])(z: A)(implicit f: (A, A) => A): Par[A] = {
    if (as.size <= 1) Par.unit(as.headOption.getOrElse(z))
    else {
      val (l, r) = as.splitAt(as.length / 2)
      Par.map2(parRun(l)(z), parRun(r)(z))(f)
    }
  }

  def sum2(as: IndexedSeq[Int]) = parRun(as)(0)(_ + _)
  def max(as: IndexedSeq[Int]) = parRun(as)(Int.MinValue)(_ max _)

  def map3[A, B, C, D](a: Par[A], b: Par[B], c: Par[C])(f: (A, B, C) => D): Par[D] = map2(map2(a, b)((a, b) => (a, b)), c) {
    case ((a, b), c) => f(a, b, c)
  }

  def map4[A, B, C, D, E](a: Par[A], b: Par[B], c: Par[C], d: Par[D])(f: (A, B, C, D) => E): Par[E] = map2(map3(a, b, c)((a, b, c) => (a, b, c)), d) {
    case ((a, b, c), d) => f(a, b, c, d)
  }

  def map5[A, B, C, D, E, F](a: Par[A], b: Par[B], c: Par[C], d: Par[D], e: Par[E])(f: (A, B, C, D, E) => F): Par[F] = map3(map3(a, b, c)((a, b, c) => (a, b, c)), d, e) {
    case ((a, b, c), d, e) => f(a, b, c, d, e)
  }

  def equal[A](e: ExecutorService)(p: Par[A], p2: Par[A]): Boolean =
    p(e).get == p2(e).get

  def choice[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] = es => {
    if (run(es)(a).get()) ifTrue(es) else ifFalse(es)
  }

  def choiceN[A](n: Par[Int])(choices: List[Par[A]]): Par[A] =
    es => {
      val ind = run(es)(n).get // Notice we are blocking on the result of `cond`.
      run(es)(choices(ind))
    }

  def choiceViaChoiceN[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] =
    choiceN(map(a)(b => if (b) 1 else 0))(List(ifTrue, ifFalse))

  def choiceMap[A, B](a: Par[A])(choices: Map[A, Par[B]]): Par[B] = es => {
    val ak = run(es)(a).get
    run(es)(choices(ak))
  }

  def chooser[A, B](a: Par[A])(choices: A => Par[B]): Par[B] = es => {
    val as = run(es)(a).get
    run(es)(choices(as))
  }

  def flatMap[A, B](a: Par[A])(f: A => Par[B]): Par[B] = es => {
    val as = run(es)(a).get
    run(es)(f(as))
  }

  def join[A](a: Par[Par[A]]): Par[A] = es => {
    val as = run(es)(a).get
    as(es)
  }

  def joinViaFlatMap[A](a: Par[Par[A]]): Par[A] =
    flatMap(a)(x => x)

  def flatMapViaJoin[A, B](p: Par[A])(f: A => Par[B]): Par[B] =
    join(map(p)(f))
}