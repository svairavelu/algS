package problems.p31

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import problems.P31

@RunWith(classOf[JUnitRunner])
class SolutionSuite extends FunSuite {
	
  import P31._
  
  test("A prime number") {
	  assert(isPrime(7))
  }
  
  test("Not a prime number") {
    assert(isPrime(18) == false)
  }
  
}