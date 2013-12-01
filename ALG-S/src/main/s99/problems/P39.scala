package problems

object P39 {

  import P31._

  def listPrimesinRange(lo: Int, hi: Int): List[Int] = {
    def f(list: List[Int], primes: Stream[Int]): List[Int] = {
      val hd = primes.head
      hd match {
        case x if x < lo => f(list, primes.tail)
        case x if x >= lo && x <= hi => f(x :: list, primes.tail)
        case x if x > hi => list
      }
    }

    f(List.empty[Int], primes).reverse
  }

  def listPrimesinRange(r: Range): List[Int] =
    primes dropWhile { _ < r.head } takeWhile { _ <= r.last } toList
}