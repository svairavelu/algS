package problems.graph

object App extends App {

  val g = Graph.term(List('b', 'c', 'd', 'f', 'g', 'h', 'k'),
           List(('b', 'c'), ('b', 'f'), ('c', 'f'), ('f', 'k'), ('g', 'h')))
           
  println(g)
  
  println( Graph.fromString("[b-c, f-c, g-h, d, f-b, k-f, h-g]").toTermForm)
  
  println(Digraph.fromStringLabel("[p>q/9, m>q/7, k, p>m/5]").toAdjacentForm)
//  println(HelloFactory.fromStr(0, "world"))
  
}
//
//abstract class Factory {
//  type Item
//  
////  def create(mkItem: => Item): Item = mkItem
////  
////  def helloMessage(name: String) = StringFactory.create(s"hello, $name")
////  
////  def add10(n: Int) = IntFactory.create(n + 10)
////  
//  def mk(s: String): Item
//  
//  def fromStr(op: Int, s: String) = op match {
//    case 0 => HelloFactory.mk(s)
//    case _ => ByeFactory.mk(s)
//  } 
//}
//
//object HelloFactory extends Factory {
//  type Item = String
//  def mk(s: String) = s"hello, $s"
//}
//
//object ByeFactory extends Factory {
//  type Item = String
//  def mk(s: String) = s"bye, $s"
//}
//
