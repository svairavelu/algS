package fp.monoid

object App extends App {

  import Monoid._

  println(frequencyMap(Vector("a rose", "is a", "rose is", "a rose")))
}