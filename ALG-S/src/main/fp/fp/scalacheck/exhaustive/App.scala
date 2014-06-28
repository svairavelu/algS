package fp.scalacheck.exhaustive

import fp.state.RNG

object App extends App {

  val xs = Stream.from(0).take(10).map(Some(_))
  println(xs)
  val simple = Gen.choose(0, 10)
  val run = Prop.forAll(simple)(x => x < 10).run
  val result = run(10, 10, RNG.Simple(0))
  result match {
    case Left(s) => println("failed with >>" + s)
    case Right((s, n)) => println(s + " " + n)
  }

  val list = Gen.listOfN(10, simple)
  val rt = Prop.forAll(list)(l => l.size == 10).run
  val rt1 = rt(10, 10, RNG.Simple(0))
  rt1 match {
    case Left(s) => println("failed with >>" + s)
    case Right((s, n)) => println(s + " " + n)
  }
}