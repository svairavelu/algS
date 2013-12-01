package problems

object P41 {

  import P40._
  
  def goldbachList(r: Range)(f: (Int, Int, Int) => Unit) = {
    for(x <- r if x % 2 == 0) {
      val (l, r) = x.goldbach
      f(x, l, r)
    }
  }
  
  def printGoldbachList(r: Range) = goldbachList(r) {
    (x, l, r) => println(x + " = " + l + " + " + r)
  }
  
  def main(args: Array[String]) {
     printGoldbachList(9 to 20)
  }
}