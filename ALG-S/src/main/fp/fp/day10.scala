package fp

object day10 extends App {
  import fp.state.RNG
  import fp.state.RNG._
  var rng: RNG = Simple(11)

  val randomInts = for {
    i <- 0 until 10
  } yield {
    val (x, rng2) = positiveIntViaFlatMap(rng)
    rng = rng2
    x
  }

  println(randomInts)

  import fp.state.State
  import fp.state.State._
  val int = State[RNG, Int](rng => rng.nextInt)

  def ints(n: Int): State[RNG, List[Int]] =
    if (n == 0) State.unit(Nil)
    else State(
      rng => {
        val (x, rng1) = rng.nextInt
        val (xs, rng2) = ints(n - 1).run(rng1)
        (x :: xs, rng2)
      })

  val zs = for {
    x <- int
    y <- int
    xs <- ints(x)
  } yield xs.map(_ % y)
  val seed = Simple(7)
  println(zs.run(seed))
}