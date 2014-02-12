package fp

object day8 extends App {

  import fp.datastructures._

  def from(z: Int, limit: Int): List[Int] =
    if (limit == 0) Nil
    else {
      Cons(z, from(z + 1, limit - 1))
    }

  val smallIntsList = from(0, 1000)

  val resultOne = List.foldLeft(smallIntsList, 0)(_ + _)
  println(resultOne)

  val largeIntsList = from(0, 1000)
  val resultTwo = List.foldRightViaFoldLeft_1(largeIntsList, 0)(_ + _)
  println(resultTwo)
}