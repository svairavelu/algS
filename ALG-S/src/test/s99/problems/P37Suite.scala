package problems

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite


@RunWith(classOf[JUnitRunner])
class P37Suite extends FunSuite  {

  import P37._
  
  test("10.phi == 4") {
    assert(10.phi == 4)
  }
}