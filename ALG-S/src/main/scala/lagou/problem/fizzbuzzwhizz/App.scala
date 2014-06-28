package lagou.problem.fizzbuzzwhizz

object App extends App {

  def count(a: Int, b: Int, c: Int): List[String] = {
    require(a < 10 && a > 0)
    require(b < 10 && b > 0)
    require(c < 10 && c > 0)
    require(a != b)
    require(a != c)
    require(b != c)
    val abc = (a :: b :: c :: Nil).sorted
    val words = Map(a -> "Fizz", b -> "Buzz", c -> "Whizz")
    def go(n: Int, list: List[String]): List[String] =
      if (n > 100) list.reverse
      else {
        val word = abc.foldLeft("")((m, x) => {
          if (n % x == 0) m + words(x)
          else m
        })
        if (word.isEmpty()) {
          go(n + 1, "" + n :: list)
        } else go(n + 1, word :: list)
      }
    go(1, Nil)
  }

  println(count(3, 5, 7).mkString("\n"))
}