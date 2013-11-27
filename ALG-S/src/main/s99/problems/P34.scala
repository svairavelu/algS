package problems

object P34 {

  import P33._

  class Wrapper(val x: Int) {
    val p33 = P33.wrap(x)
    def totient(): Int = {
      var count = 0
      for (i <- 1 until x if (p33.isCoprimeTo(i))) {
        count += 1
      }

      count
    }
  }
  
  implicit def wrap(x: Int) = new Wrapper(x)
}