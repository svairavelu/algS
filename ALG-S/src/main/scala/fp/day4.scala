package fp

object day4 extends App {

  import fp.errorhandling._
  import Either._

  case class Person(name: Name, age: Age)
  sealed class Name(val value: String)
  sealed class Age(val value: Int)

  def mkName(name: String): Either[String, Name] =
    if (name == "" || name == null) Left("Name is empty.")
    else Right(new Name(name))
  def mkAge(age: Int): Either[String, Age] =
    if (age < 0) Left("Age is out of range.")
    else Right(new Age(age))
  def mkPerson(name: String, age: Int): Either[String, Person] =
    mkName(name).map2(mkAge(age))(Person(_, _))

  val p1 = mkPerson("test", 10)
  println(p1)
  val p2 = mkPerson("minus age", -1)
  println(p2)
  val p3 = mkPerson("", 10)
  println(p3)

  import fp.laziness.Stream
  import Stream._

  Stream(1, 2, 3).take(2).toList foreach print
  println
  println(Stream(1, 2, 3).forAll(_ > 0)) 
  println(Stream(1, 2, 3).exists(_ > 3))
}