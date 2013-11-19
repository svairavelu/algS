package problems

import problems.P32._

class P33(val x: Int) {
  import P33._
  def isCoprimeTo(y: P33) = gcd(x, y) == 1
}

object P33 {

  implicit def wrap(x: Int): P33 = new P33(x)
  implicit def unwrap(p: P33): Int = p.x
}