package problems.multiwaytree

object P70 extends App {

  import MTree._

  println(MTree('a', List(MTree('f', List(MTree('g'))), MTree('c'), MTree('b', List(MTree('d'), MTree('e'))))).nodeCount)

  println(MTree('a', List(MTree('f', List(MTree('g'))), MTree('c'), MTree('b', List(MTree('d'), MTree('e'))))).toString)
  
  println("afg^^c^bd^e^^".internalPathLength)
  
  println("afg^^c^bd^e^^".postorder)
  
  println("afg^^c^bd^e^^".lispyTree)
}