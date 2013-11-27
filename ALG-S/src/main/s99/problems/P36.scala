/**
 *
 */
package problems

/**
 * @author Blues
 *
 */

// But we can do it directly.
class P36(val start: Int) {
  import P31._

  def primeFactorMultiplicity: Map[Int, Int] = {
    def factorCount(n: Int, p: Int): (Int, Int) =
      if (n % p != 0) (0, n)
      else factorCount(n / p, p) match { case (c, d) => (c + 1, d) }
    def factorsR(n: Int, ps: Stream[Int]): Map[Int, Int] =
      if (n == 1) Map()
      else if (n.isPrime) Map(n -> 1)
      else {
        val nps = ps.dropWhile(n % _ != 0)
        val (count, dividend) = factorCount(n, nps.head)
        Map(nps.head -> count) ++ factorsR(dividend, nps.tail)
      }
    factorsR(start, primes)
  }

  // This also lets us change primeFactors.
  def primeFactors: List[Int] =
    primeFactorMultiplicity flatMap { v => List.make(v._2, v._1) } toList
}

object P36 {
  implicit def wrap(x: Int) = new P36(x)
}