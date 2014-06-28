package codejam.year2004.round3

import scala.io.Source

object C extends App {

  val file = "src/main/scala/codejam/year2004/round3/C-large-practice.in"

  val lines = Source.fromFile(file).getLines()

  val T = lines.next().toInt

  class Event(val no: Int, val at: Int) {
    def isEnter: Boolean = this match {
      case Enter(_, _) => true
      case _ => false
    }
    def isLeave: Boolean = this match {
      case Leave(_, _) => true
      case _ => false
    }
  }

  case class Enter(override val no: Int, override val at: Int) extends Event(no, at)
  case class Leave(override val no: Int, override val at: Int) extends Event(no, at)

  object Event {
    def apply(x: String, no: Int, at: Int) =
      if (x == "E") Enter(no, at)
      else Leave(no, at)
  }
  def nextExitEvent(nextKnownEvent: Map[Int, List[Event]], inside: Set[Int]): Option[Int] = {
    val evts =
      nextKnownEvent.filter(x => !inside.contains(x._1) && x._2.size > 0 && x._2.head.isLeave).map(x => x._2.head).toList.sortBy(_.at)
    if (evts.isEmpty) {
      None
    } else {
      Some(evts.head.no)
    }
  }

  def nextEnterEvent(nextKnownEvent: Map[Int, List[Event]], inside: Set[Int]): Option[Int] = {
    val evts =
      nextKnownEvent.filter(x => inside.contains(x._1) && x._2.size > 0 && x._2.head.isEnter).map(x => x._2.head).toList.sortBy(_.at)
    if (evts.isEmpty) {
      None
    } else {
      Some(evts.head.no)
    }
  }

  def chooseOneLeave(inside: List[Int], nextKnownEvent: Map[Int, List[Event]]): Option[Int] =
    inside match {
      case Nil => None
      case x :: tail if (!nextKnownEvent.contains(x) || nextKnownEvent(x).size == 0) => Some(x)
      case x :: tail => chooseOneLeave(tail, nextKnownEvent)
    }

  def chooseTheLastOneLeave(nextKnownEvent: Map[Int, List[Event]], inside: Set[Int]): Option[Int] = {
    val evts =
      nextKnownEvent.filter(x => inside.contains(x._1) && x._2.size > 0 && x._2.head.isLeave).map(x => x._2.head).toList.sortBy(_.at)
    if (evts.isEmpty) {
      None
    } else {
      Some(evts.last.no)
    }
  }

  def monitor(evts: List[Event], inside: Set[Int], number: Int, nextKnownEvent: Map[Int, List[Event]]): Option[Int] =
    evts match {
      case Nil => Some(inside.size)
      case h :: tail =>
        h match {
          case Enter(0, _) =>
            nextExitEvent(nextKnownEvent, inside) match {
              case Some(x) =>
                monitor(tail, inside + x, number, nextKnownEvent)
              case None =>
                monitor(tail, inside + number, number + 1, nextKnownEvent)
            }
          case Enter(x, _) =>
            if (inside.contains(x)) None
            else monitor(tail, inside + x, number, nextKnownEvent + (x -> nextKnownEvent(x).tail))
          case Leave(0, _) =>
            if (inside.isEmpty) None
            else nextEnterEvent(nextKnownEvent, inside) match {
              case Some(x) => monitor(tail, inside - x, number, nextKnownEvent)
              case None =>
                chooseOneLeave(inside.toList, nextKnownEvent) match {
                  case Some(x) => monitor(tail, inside - x, number, nextKnownEvent)
                  case None => chooseTheLastOneLeave(nextKnownEvent, inside) match {
                    case Some(x) => monitor(tail, inside - x, number, nextKnownEvent)
                    case None => None
                  }
                }
            }
          case Leave(x, _) =>
            if (inside.contains(x)) {
              monitor(tail, inside - x, number, nextKnownEvent + (x -> nextKnownEvent(x).tail))
            } else {
              None
            }
        }
    }

  def bs(left: Int, right: Int, rs: Option[Int], evts: List[Event], number: Int, nextKnownEvent: Map[Int, List[Event]]): Option[Int] =
    if (left > right) rs
    else {
      val mid = (left + right) / 2
      val assumed = List.fill(mid)(Enter(0, -1)) ++ evts
      monitor(assumed, Set.empty, number, nextKnownEvent) match {
        case Some(x) =>
          bs(left, mid - 1, Some(x), evts, number, nextKnownEvent)
        case None =>
          bs(mid + 1, right, rs, evts, number, nextKnownEvent)
      }
    }

  def nextKnownEvent(evts: List[Event], map: Map[Int, List[Event]]): Map[Int, List[Event]] =
    evts match {
      case Nil =>
        map.map {
          case (k, v) => (k, v.reverse)
        }
      case h :: tail if (h.no > 0) =>
        nextKnownEvent(tail, map + (h.no -> (h :: map(h.no))))
      case h :: tail =>
        nextKnownEvent(tail, map)
    }
  (1 to T).foreach {
    t =>
      val n = lines.next().toInt
      val evts =
        ((0 until n).foldLeft(Nil: List[Event]) {
          (evts, i) =>
            val evt = lines.next.split("\\s+")
            Event(evt(0), evt(1).toInt, i) :: evts
        }).reverse

      bs(0, n, None, evts, 2001, nextKnownEvent(evts, Map.empty.withDefaultValue(Nil))) match {
        case Some(x) => println(s"Case #$t: $x")
        case None => println(s"Case #$t: CRIME TIME")
      }
  }
}