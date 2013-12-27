package com.me.problems.alg

object GSMatch {

  sealed class Arrangement
  case object Sail extends Arrangement
  case class Stop(at: Int) extends Arrangement

  class Boat(val label: Int, var arrangments: List[Arrangement]) {
    var at: Int = _
    var in: Int = _

    override def equals(that: Any) = that match {
      case x: Boat => label == x.label
      case _ => false
    }
  }

  object Boat {
    def apply(label: Int, arrangements: List[Arrangement]) = new Boat(label, arrangements)
  }

  def arrange(boats: List[Boat], m: Int) {
    var schedule: Map[Int, Boat] = Map.empty[Int, Boat]
    var unchecked = boats.toSet
    def stop(boat: Boat, arrangements: List[Arrangement], m: Int): Unit = arrangements match {
      case Stop(at) :: tail =>
        if (schedule.contains(at)) {
          val previous = schedule(at)
          boat.at = at
          boat.in = m
          schedule += (at -> boat)
          unchecked += previous
        } else {
          boat.at = at
          boat.in = m
          boat.arrangments = tail
          schedule += (at -> boat)
        }
      case Sail :: tail => stop(boat, tail, m + 1)
      case Nil =>
    }

    while (!unchecked.isEmpty) {
      val boat = unchecked.head
      unchecked -= boat
      stop(boat, boat.arrangments, boat.in + 1)
    }
  }

  def main(args: Array[String]): Unit = {
    val m = 4
    val boat1 = Boat(1, List(Sail, Stop(2), Sail, Stop(1)).reverse)
    val boat2 = Boat(2, List(Stop(2), Sail, Stop(1), Sail).reverse)
    arrange(List(boat1, boat2), 4)

    println(s"boat1 stops at ${boat1.at} in ${boat1.in}")
    println(s"boat2 stops at ${boat2.at} in ${boat2.in}")
  }
}