package problems

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite


@RunWith(classOf[JUnitRunner])
class P39Suite extends FunSuite  {

  import P39._
  
  test("listPrimesinRange(7 to 31)") {
    assert(listPrimesinRange(7, 31) == List(7, 11, 13, 17, 19, 23, 29, 31))
  }
  
}