package fp

object day5 extends App {

  import fp.laziness._
  import Stream._

  val fibs = {
    def go(a: Int, b: Int): Stream[Int] = {
      cons(a, go(b, a + b))
    }

    go(0, 1)
  }

  fibs.take(10).toList.foreach(x => print(s"$x "))
  println
  val less11 = unfold(1)(x => if (x > 10) None else Some(x, x + 1))
  println(less11)
  less11.toList.foreach(x => print(s"$x "))
  println
  val inf = unfold(1)(x => Some(x, x + 1))
  inf.take(100).toList.foreach(x => print(s"$x "))
  println
  val fibs1 = unfold((0, 1)) {
    case (a, b) => Some(a, (b, a + b))
  }
  fibs1.take(10).toList.foreach(x => print(s"$x "))
  println
  val negInf = inf.mapWithUnfold(x => -x)
  negInf.take(10).toList.foreach(x => print(s"$x "));
  println
  negInf.takeWithUnfold(10).toList.foreach(x => print(s"$x "));
  println
  inf.zip(negInf).takeWithUnfold(10).toList.foreach(x => print(s"$x "));
  println
  inf.take(20).zipAll(negInf.take(10)).toList.foreach(x => print(s"$x "));
  println
  val tails = Stream(1, 2, 3).tails
  tails.toList.foreach(x => println(x.toList))
  println
  println(Stream(1, 2, 3).scanRight(0)(_ + _).toList)
}