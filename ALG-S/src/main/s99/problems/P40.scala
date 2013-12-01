package problems

object P40 {

  import P31._
  implicit class Wrapper(val x: Int) extends AnyVal {
    def goldbach: (Int, Int) = primes takeWhile { _ < x } find { p => (x - p).isPrime } match {
      case None => throw new IllegalArgumentException
      case Some(p1) => (p1, x - p1)
    }
  }
}