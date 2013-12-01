package problems.logical

object P49 {
  def gray(n: Int): List[String] =
    if (n == 0) List("")
    else {
      val lower = gray(n - 1)
      (lower map { "0" + _ }) ::: (lower.reverse map { "1" + _ })
    }

  import scala.collection.mutable
  private val strings = mutable.Map(0 -> List(""))
  def grayMemoized(n: Int): List[String] = {
    if (!strings.contains(n)) {
      strings += (n -> ((grayMemoized(n - 1) map { "0" + _ }) :::
        (grayMemoized(n - 1).reverse map { "1" + _ })))
    }
    strings(n)
  }
  
  def main(args: Array[String]) {
    grayMemoized(3).foreach(println)
  }
}