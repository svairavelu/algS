package codejam.year2015.apactest

import scala.io.Source

object A extends App {

  val file = "src/main/scala/codejam/year2015/apactest/A-console.in"

  val lines = Source.fromFile(file).getLines()

  val T = lines.next().toInt

  def process(t: Int): Unit =
    if (t <= T) {
      val line = lines.next().split("\\s+")
      val n = line(0).toInt

      //      val first = xs.zip(line(1).toCharArray().map(_.toInt)).map(x => State(x._1, x._2))
      def find(num: Num, i: Int): Option[Num] =
        if (i > n) Some(num)
        else {
          val xs = line(i).toCharArray().map(_ - '0').toList
          num.check(xs) match {
            case Left(_) => None
            case Right(bs) => find(num.next(bs), i + 1)
          }
        }

      val nums = (0 to 9).foldLeft(Nil: List[Num])((xs, i) => Num(i, List.fill(7)(-1)) :: xs)

      val xs =
        (for {
          x <- nums
        } yield find(x, 1)).filterNot(_.isEmpty)

      xs match {
        case List(x) => println(s"Case #$t: ${x.get.output}")
        case _ => println(s"Case #$t: ERROR!")
      }

      process(t + 1)
    }

  process(1)
}

abstract class Num(val x: Int, val broken: List[Int]) {
  type State = List[Int]
  def fullState: State
  def state: State = {
    def go(fl: State, bl: State): State = (fl, bl) match {
      case (Nil, Nil) => Nil
      case (1 :: ft, 1 :: bt) => 1 :: go(ft, bt)
      case (1 :: ft, -1 :: bt) => 1 :: go(ft, bt)
      case (_ :: ft, _ :: bt) => 0 :: go(ft, bt)
    }

    go(fullState, broken)
  }

  def next(broken: State): Num =
    if (x == 0) Num(9, broken)
    else Num(x - 1, broken)

  def check(zs: State): Either[Boolean, State] = {
    def go(ys: State, zs: State, bs: State): State = (ys, zs, bs) match {
      case (Nil, Nil, Nil) => Nil
      case (1 :: yt, 1 :: zt, h :: bt) if (h != 0) => 1 :: go(yt, zt, bt)
      case (0 :: yt, 0 :: zt, h :: bt) => h :: go(yt, zt, bt)
      case (1 :: yt, 0 :: zt, h :: bt) if (h <= 0) => 0 :: go(yt, zt, bt)
      case (_, _, _) => throw new Exception("Not match")
    }

    try {
      Right(go(state, zs, broken))
    } catch {
      case e: Exception => Left(false)
    }
  }

  def output = state mkString ("")
}

class Zero(override val broken: List[Int]) extends Num(0, broken) {
  def fullState = ("1111110".toCharArray().map(_ - '0')).toList
}

class One(override val broken: List[Int]) extends Num(1, broken) {
  override def fullState = ("0110000".toCharArray().map(_ - '0')).toList
}

class Two(override val broken: List[Int]) extends Num(2, broken) {
  override def fullState = ("1101101".toCharArray().map(_ - '0')).toList
}

class Three(override val broken: List[Int]) extends Num(3, broken) {
  override def fullState = ("1111001".toCharArray().map(_ - '0')).toList
}

class Four(override val broken: List[Int]) extends Num(4, broken) {
  override def fullState = ("0110011".toCharArray().map(_ - '0')).toList
}

class Five(override val broken: List[Int]) extends Num(5, broken) {
  override def fullState = ("1011011".toCharArray().map(_ - '0')).toList
}

class Six(override val broken: List[Int]) extends Num(6, broken) {
  override def fullState = ("1011111".toCharArray().map(_ - '0')).toList
}

class Seven(override val broken: List[Int]) extends Num(7, broken) {
  override def fullState = ("1110000".toCharArray().map(_ - '0')).toList
}

class Eight(override val broken: List[Int]) extends Num(8, broken) {
  override def fullState = ("1111111".toCharArray().map(_ - '0')).toList
}

class Nine(override val broken: List[Int]) extends Num(9, broken) {
  override def fullState = ("1111011".toCharArray().map(_ - '0')).toList
}

object Num {
  def apply(x: Int, broken: List[Int]): Num = x match {
    case 0 => new Zero(broken)
    case 1 => new One(broken)
    case 2 => new Two(broken)
    case 3 => new Three(broken)
    case 4 => new Four(broken)
    case 5 => new Five(broken)
    case 6 => new Six(broken)
    case 7 => new Seven(broken)
    case 8 => new Eight(broken)
    case 9 => new Nine(broken)
  }
}

