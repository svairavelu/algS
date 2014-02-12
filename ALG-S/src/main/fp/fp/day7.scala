package fp

import java.util.concurrent.Executors

object day7 extends App {

  import fp.parallelism._
  import Par._

  val a = async(42 + 1)
  val S = Executors.newFixedThreadPool(1)
  println(Par.equal(S)(a, fork(a)))
}