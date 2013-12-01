package problems

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite


@RunWith(classOf[JUnitRunner])
class P40Suite extends FunSuite {
  import P40._
  
  test("28.goldbach == (5, 23)") {
    assert(28.goldbach == (5, 23))
  }

}