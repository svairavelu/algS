package fp

object day6 extends App {

  import fp.state._

  import RNG._

  val seed = 1000
  val inputs = List(Coin, Turn, Coin, Coin, Turn)
  val sim = Candy.simulateMachine(inputs)

  val ((candis, coins), _) = sim.run(Machine(false, 10, 2))

  println(candis + " " + coins)
}