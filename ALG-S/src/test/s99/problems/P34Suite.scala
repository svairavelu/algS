package problems

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class P34Suite extends FunSuite {

  import P34._
  test("10 totient == 4") {
	  assert(10.totient == 4, "")
  }
}