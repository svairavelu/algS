/**
 *
 */
package problems

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * @author Blues
 *
 */
@RunWith(classOf[JUnitRunner])
class P33Suite extends FunSuite {
	
  import P33._
  
  test("35.isCoprimeTo(64) equals true") {
    assert(35.isCoprimeTo(64))
  }
  
  test("2.isCoprimeTo(4) equals false") {
    assert(2.isCoprimeTo(4) == false)
  }
  
}