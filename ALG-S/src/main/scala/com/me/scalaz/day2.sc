package com.me.scalaz

object day2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  import scalaz._
  import Scalaz._

  (1, 2, 3) map { _ + 1 }                         //> res0: (Int, Int, Int) = (1,2,4)

  val f = ((x: Int) => x + 1) map { _ * 7 }       //> f  : Int => Int = <function1>

  f(30)                                           //> res1: Int = 217

  List(1, 2, 3) >| "x"                            //> res2: List[String] = List(x, x, x)

  List(1, 2, 3) as "x"                            //> res3: List[String] = List(x, x, x)

  List(1, 2, 3).fpair                             //> res4: List[(Int, Int)] = List((1,1), (2,2), (3,3))

  List(1, 2, 3).strengthL("x")                    //> res5: List[(String, Int)] = List((x,1), (x,2), (x,3))

  List(1, 2, 3).strengthR("x")                    //> res6: List[(Int, String)] = List((1,x), (2,x), (3,x))

  List(1, 2, 3).void                              //> res7: List[Unit] = List((), (), ())

  val ff = List(1, 2, 3, 4) map { (_: Int) * (_: Int) }.curried
                                                  //> ff  : List[Int => Int] = List(<function1>, <function1>, <function1>, <functi
                                                  //| on1>)
  ff map { _(9) }                                 //> res8: List[Int] = List(9, 18, 27, 36)

	1.point[List]                             //> res9: List[Int] = List(1)

	1.point[Option]                           //> res10: Option[Int] = Some(1)
	
	1.point[Option] map {_ + 2}               //> res11: Option[Int] = Some(3)
	
	1.point[List] map {_ + 2}                 //> res12: List[Int] = List(3)
	
	
	9.some <*> {(_: Int) + 3}.some            //> res13: Option[Int] = Some(12)
	
	1.some <* 2.some                          //> res14: Option[Int] = Some(1)
	
	1.some *> 2.some                          //> res15: Option[Int] = Some(2)
	
	none *> 2.some                            //> res16: Option[Int] = None

	3.some <*> { 9.some <*> {(_: Int) + (_: Int)}.curried.some }
                                                  //> res17: Option[Int] = Some(12)
	
	^(3.some, 5.some) {_ + _}                 //> res18: Option[Int] = Some(8)
	
	
	^(3.some, none: Option[Int]) {_ + _}      //> res19: Option[Int] = None
	
	(3.some |@| 5.some) {_ + _}               //> res20: Option[Int] = Some(8)
	
	List(1, 2, 3) <*> List((_: Int) * 0, (_: Int) + 100, (x: Int) => x * x)
                                                  //> res21: List[Int] = List(0, 0, 0, 101, 102, 103, 1, 4, 9)
	List(3, 4) <*> { List(1, 2) <*> List({(_: Int) + (_: Int)}.curried, {(_: Int) * (_: Int)}.curried) }
                                                  //> res22: List[Int] = List(4, 5, 5, 6, 3, 4, 6, 8)
	
	(List("ha", "heh", "hmm") |@| List("?", "!", ".")) {_ + _}
                                                  //> res23: List[String] = List(ha?, ha!, ha., heh?, heh!, heh., hmm?, hmm!, hmm
                                                  //| .)
	val myRes1 = streamZipApplicative.ap(Tags.Zip(Stream(1, 2))) (Tags.Zip(Stream({(_: Int) + 3}, {(_: Int) * 2})))
                                                  //> myRes1  : scala.collection.immutable.Stream[Int] with Object{type Tag = sca
                                                  //| laz.Tags.Zip} = Stream(4, ?)
	myRes1.toList                             //> res24: List[Int] = List(4, 4)
}