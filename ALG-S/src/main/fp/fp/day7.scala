package fp

import java.util.concurrent.Executors

object day7 extends App {

  import fp.parallelism._
  import Par._

  def sum(as: IndexedSeq[Int]): Par[Int] =
    if (as.isEmpty) Par.unit(0)
    else {
      val (l, r) = as.splitAt(as.length / 2)
      Par.map2(Par.fork(sum(l)), Par.fork(sum(r)))(_ + _)
    }

  val a = async(42 + 1)
  val S = Executors.newFixedThreadPool(2)
  println(Par.equal(S)(a, fork(a)))
}