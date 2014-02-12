package fp

object week1 extends App {

  def fib(n: Int): Int = {
    def go(a: Int, b: Int, k: Int): Int = {
      if (k > n) b
      else go(b, a + b, k + 1)
    }

    go(1, 1, 2)
  }

  def formatResult(f: Int => Int)(name: String)(n: Int) = {
    val msg = "the %s of %d is %d.";
    msg.format(name, n, f(n))
  }
  //  println(fib(0))
  //  println(fib(1))
  //  println(fib(2))
  //  println(fib(3))

  def formatFibResult = formatResult(fib)("fib") _

  println(formatFibResult(0))
  println(formatFibResult(1))
  println(formatFibResult(2))
  println(formatFibResult(3))
  println(formatFibResult(4))

  def isSorted[A](xs: Array[A], gt: (A, A) => Boolean): Boolean = {
    def go(idx: Int): Boolean = {
      if (idx >= xs.length) true
      else {
        val x = xs(idx - 1)
        val y = xs(idx)
        val greater = gt(x, y)
        if (greater) {
          false
        } else {
          go(idx + 1)
        }
      }
    }

    go(1)
  }

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = f(a, _)

  def fForPartial1Test(i: Int, d: Double): String = i match {
    case x if x < 0 => s"when $x less that 0, got a ${-d}"
    case _ => s"when $i equals/greater 0, got a ${d}"
  }

  val paritalled = partial1(-1, fForPartial1Test)

  println(paritalled(3.0))

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = (a: A) => (b: B) => f(a, b)

  def uncurry[A, B, C](f: A => B => C): (A, B) => C = f(_)(_)

  def toTestUnCurry(a: Int)(b: Double): String = s"get $a then get $b"

  def uncurried = uncurry(toTestUnCurry)
  println(uncurried(10, 2.0))

  def compose[A, B, C](f: B => C, g: A => B): A => C = a => f(g(a))
}