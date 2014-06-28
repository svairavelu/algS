package fp.monad

import scala.language.higherKinds
trait Applicative[F[_]] extends Functor[F] {
  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] = {
    val abc = unit((a: A) => (b: B) => f(a, b))
    val bc = apply(abc)(fa)
    apply(bc)(fb)
  }
  def apply[A, B](fab: F[A => B])(fa: F[A]): F[B]
  def unit[A](a: => A): F[A]
  //  def _map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C]
  //
  //  def _apply[A, B](fab: F[A => B])(fa: F[A]): F[B] = _map2(fab, fa)((f, a) => f(a))

  def map[A, B](a: F[A])(f: A => B): F[B] =
    apply(unit(f))(a)

  def _map[A, B](a: F[A])(f: A => B): F[B] =
    map2(unit(f), a)(_(_))

  def factor[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    map2(fa, fb)((_, _))

  def product[G[_]](G: Applicative[G]): Applicative[({ type f[x] = (F[x], G[x]) })#f] = {
    val self = this
    new Applicative[({ type f[x] = (F[x], G[x]) })#f] {
      def unit[A](a: => A) = (self.unit(a), G.unit(a))
      override def apply[A, B](fs: (F[A => B], G[A => B]))(p: (F[A], G[A])) =
        (self.apply(fs._1)(p._1), G.apply(fs._2)(p._2))
    }
  }

  // Here we simply use `map2` to lift `apply` and `unit` themselves from one
  // Applicative into the other.
  // If `self` and `G` both satisfy the laws, then so does the composite.
  // The full proof can be found at
  // https://github.com/runarorama/sannanir/blob/master/Applicative.v
  def compose[G[_]](G: Applicative[G]): Applicative[({ type f[x] = F[G[x]] })#f] = {
    val self = this
    new Applicative[({ type f[x] = F[G[x]] })#f] {
      def unit[A](a: => A) = self.unit(G.unit(a))
      def apply[A, B](fab: F[G[A => B]])(fa: F[G[A]]): F[G[B]] = {
        self.map2(fab, fa)((gf, ga) => G.apply(gf)(ga))
      }
    }
  }

  def sequenceMap[K, V](ofa: Map[K, F[V]]): F[Map[K, V]] =
    if (ofa.isEmpty) unit(Map())
    else {
      val h = ofa.head
      val x = map(h._2)(v => (h._1, v))
      map2(x, sequenceMap(ofa.tail))((x, m) => m + x)
    }

}