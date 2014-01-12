package com.me.scalaz

object day2 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(81); 
  println("Welcome to the Scala worksheet")

  import scalaz._
  import Scalaz._;$skip(66); val res$0 = 

  (1, 2, 3) map { _ + 1 };System.out.println("""res0: (Int, Int, Int) = """ + $show(res$0));$skip(46); 

  val f = ((x: Int) => x + 1) map { _ * 7 };System.out.println("""f  : Int => Int = """ + $show(f ));$skip(10); val res$1 = 

  f(30);System.out.println("""res1: Int = """ + $show(res$1));$skip(25); val res$2 = 

  List(1, 2, 3) >| "x";System.out.println("""res2: List[String] = """ + $show(res$2));$skip(25); val res$3 = 

  List(1, 2, 3) as "x";System.out.println("""res3: List[String] = """ + $show(res$3));$skip(24); val res$4 = 

  List(1, 2, 3).fpair;System.out.println("""res4: List[(Int, Int)] = """ + $show(res$4));$skip(33); val res$5 = 

  List(1, 2, 3).strengthL("x");System.out.println("""res5: List[(String, Int)] = """ + $show(res$5));$skip(33); val res$6 = 

  List(1, 2, 3).strengthR("x");System.out.println("""res6: List[(Int, String)] = """ + $show(res$6));$skip(23); val res$7 = 

  List(1, 2, 3).void;System.out.println("""res7: List[Unit] = """ + $show(res$7));$skip(66); 

  val ff = List(1, 2, 3, 4) map { (_: Int) * (_: Int) }.curried;System.out.println("""ff  : List[Int => Int] = """ + $show(ff ));$skip(18); val res$8 = 
  ff map { _(9) };System.out.println("""res8: List[Int] = """ + $show(res$8));$skip(16); val res$9 = 

	1.point[List];System.out.println("""res9: List[Int] = """ + $show(res$9));$skip(18); val res$10 = 

	1.point[Option];System.out.println("""res10: Option[Int] = """ + $show(res$10));$skip(31); val res$11 = 
	
	1.point[Option] map {_ + 2};System.out.println("""res11: Option[Int] = """ + $show(res$11));$skip(29); val res$12 = 
	
	1.point[List] map {_ + 2};System.out.println("""res12: List[Int] = """ + $show(res$12));$skip(36); val res$13 = 
	
	
	9.some <*> {(_: Int) + 3}.some;System.out.println("""res13: Option[Int] = """ + $show(res$13));$skip(20); val res$14 = 
	
	1.some <* 2.some;System.out.println("""res14: Option[Int] = """ + $show(res$14));$skip(20); val res$15 = 
	
	1.some *> 2.some;System.out.println("""res15: Option[Int] = """ + $show(res$15));$skip(18); val res$16 = 
	
	none *> 2.some;System.out.println("""res16: Option[Int] = """ + $show(res$16));$skip(63); val res$17 = 

	3.some <*> { 9.some <*> {(_: Int) + (_: Int)}.curried.some };System.out.println("""res17: Option[Int] = """ + $show(res$17));$skip(29); val res$18 = 
	
	^(3.some, 5.some) {_ + _};System.out.println("""res18: Option[Int] = """ + $show(res$18));$skip(42); val res$19 = 
	
	
	^(3.some, none: Option[Int]) {_ + _};System.out.println("""res19: Option[Int] = """ + $show(res$19));$skip(31); val res$20 = 
	
	(3.some |@| 5.some) {_ + _};System.out.println("""res20: Option[Int] = """ + $show(res$20));$skip(75); val res$21 = 
	
	List(1, 2, 3) <*> List((_: Int) * 0, (_: Int) + 100, (x: Int) => x * x);System.out.println("""res21: List[Int] = """ + $show(res$21));$skip(102); val res$22 = 
	List(3, 4) <*> { List(1, 2) <*> List({(_: Int) + (_: Int)}.curried, {(_: Int) * (_: Int)}.curried) };System.out.println("""res22: List[Int] = """ + $show(res$22));$skip(62); val res$23 = 
	
	(List("ha", "heh", "hmm") |@| List("?", "!", ".")) {_ + _};System.out.println("""res23: List[String] = """ + $show(res$23));$skip(113); 
	val myRes1 = streamZipApplicative.ap(Tags.Zip(Stream(1, 2))) (Tags.Zip(Stream({(_: Int) + 3}, {(_: Int) * 2})));System.out.println("""myRes1  : scala.collection.immutable.Stream[Int] with Object{type Tag = scalaz.Tags.Zip} = """ + $show(myRes1 ));$skip(15); val res$24 = 
	myRes1.toList;System.out.println("""res24: List[Int] = """ + $show(res$24))}
}
