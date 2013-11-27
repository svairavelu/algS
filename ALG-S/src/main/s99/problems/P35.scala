package problems

class P35(x: Int) {
  import P31._
  def primeFactors: List[Int] = {
    def primeFactorsR(n: Int, ps: Stream[Int]): List[Int] =
      if (n.isPrime) List(n)
      else if (n % ps.head == 0) ps.head :: primeFactorsR(n / ps.head, ps)
      else primeFactorsR(n, ps.tail)
    primeFactorsR(x, primes)
  }

}

object P35 {
	implicit def wrap(x: Int) = new P35(x)
}