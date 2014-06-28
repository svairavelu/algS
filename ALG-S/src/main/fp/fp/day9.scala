package fp

object day9 extends App {

  import fp.laziness._

  println(Stream(1, 2, 3).take(0).toList)

  println(Stream(1, 2, 3).map(_ + 10).filter(_ % 2 == 1).toList)
  
  println(Stream.from(1).take(10).toList)
}