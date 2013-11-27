package problems

class P37(val x: Int) extends AnyVal {

  import P36._
  
  def phi: Int = {
    val factors = x.primeFactorMultiplicity
    
    (1 /: factors){
      case (product, x) => 
        val (p, m) = x
        product * (p - 1) * math.pow(p, m - 1).toInt
    }
    
  }
}

object P37 {
  implicit def wrap(x: Int) = new P37(x)
  
  def main(args: Array[String]) {
    println(10.phi)
  }
}