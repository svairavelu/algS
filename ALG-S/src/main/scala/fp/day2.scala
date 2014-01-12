package fp

object day2 extends App {

  import fp.datastructures._
  import List._

  println(reverse(List(1, 2, 3)))

  val ll = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))

  println(concat(ll))

  println(concat1(ll))

  val nums = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

  val oddNums = filter(nums)(_ % 2 == 1)
  println(oddNums)
  
  val pairNums = flatMap(nums)(i => List(i, i))
  
  println(pairNums)
  
  val evenNums = filterWithFlatMap(nums)(_ % 2 == 0)
  println(evenNums)
  
  val sub = List(5, 6, 7)
  
  println(hasSubsequence(nums, sub))
}