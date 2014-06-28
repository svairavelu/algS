package fp.scalacheck

object App extends App {

  val simple = Gen.choose(0, 10)
 
  println(simple.exhaustive.take(9).toList)
  
  val list = Gen.listOfN(3, simple)
  println(list.exhaustive.take(1).toList)
}