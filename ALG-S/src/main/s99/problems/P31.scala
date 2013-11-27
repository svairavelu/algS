package problems

class P31(val start: Int) {

  import P31._

  def isPrime: Boolean =
    (start > 1) && (primes takeWhile { _ <= Math.sqrt(start) } forall { start % _ != 0 })

}

object P31 {

  implicit def wrap(x: Int) = new P31(x)
  
  val primes = Stream.cons(2, Stream.from(3, 2) filter { _.isPrime })
}
// Readers interested in more sophisticated (and more efficient) primality tests
// are invited to read http://primes.utm.edu/prove/index.html .  Implementation
// in Scala is left as an exercise for the reader.

// Similarly, a more efficient, functional, lazy, infinite prime list can be found
// at http://article.gmane.org/gmane.comp.lang.haskell.cafe/19470 .  (Haskell
// implementation.)