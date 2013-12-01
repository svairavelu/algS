package problems

object P38 {

  import P34._
  import P37._
  
  def time[A](label: String)(block: => A): A = {
    val now = System.currentTimeMillis()
    val ret = block
    println(label + ": " + (System.currentTimeMillis() - now) + " ms.")
    ret
  }

  def test(n: Int) {
    time("P34 (" + n + ")") {
      n.totient
    }
    time("P37 (" + n + ")") {
      n.phi
    }
  }
  
  def main(args: Array[String]) {
    test(10090)
  }
}