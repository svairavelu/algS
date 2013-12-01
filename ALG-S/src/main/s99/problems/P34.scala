package problems

object P34 {

  import P33._

  implicit class Wrapper(val x: Int) {
    def totient(): Int = {
      var count = 0
      for (i <- 1 until x if (x.isCoprimeTo(i))) {
        count += 1
      }

      count
    }
  }
}