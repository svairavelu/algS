package problems

class S99Int(val start: Int) {

  import S99Int._

  def isPrime: Boolean =
    (start > 1) && (primes takeWhile { _ <= Math.sqrt(start) } forall { start % _ != 0 })

}

object S99Int {

  private implicit def convert(x: Int) = new S99Int(x)

  val primes = Stream.cons(2, Stream.from(3, 2) filter { _.isPrime })

}

object P31 {
  import S99Int._
  def isPrime(x: Int) = {
	  val took = primes.takeWhile(_ <= x).last
	  took == x
  }
}
// Readers interested in more sophisticated (and more efficient) primality tests
// are invited to read http://primes.utm.edu/prove/index.html .  Implementation
// in Scala is left as an exercise for the reader.

// Similarly, a more efficient, functional, lazy, infinite prime list can be found
// at http://article.gmane.org/gmane.comp.lang.haskell.cafe/19470 .  (Haskell
// implementation.)