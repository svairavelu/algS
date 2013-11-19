/**
 *
 */
package problems.p32

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import problems.P32

/**
 * @author Blues
 *
 */
@RunWith(classOf[JUnitRunner])
class P32Suite extends FunSuite {

  import P32._

  test("gcd(36, 63) == 9") {
    assert(gcd(36, 63) == 9)
  }

  test("gcd(48, 18) == 6") {
    assert(gcd(48, 18) == 6)
  }

  test("gcd(-48, 18) == -6") {
    assert(gcd(-48, 18) == 6)
  }
  
}