package codejam.meetandparty

object App extends App {

  val T = readLine.toInt

  def process(t: Int): Unit = {
    if (t <= T) {
      val N = readLine.toInt

      var xs = List.empty[Long]
      var ys = List.empty[Long]
      var pairs = List.empty[(Long, Long)]

      def read(n: Int): Unit = {
        if (n < N) {
          val line = readLine.split("\\s+").map(_.toLong)
          for {
            i <- line(0) to line(2)
            j <- line(1) to line(3)
          } {
            xs = i :: xs
            ys = j :: ys
            pairs = (i, j) :: pairs
          }
          read(n + 1)
        }
      }
      read(0)

      xs = xs.sorted
      ys = ys.sorted
      pairs = pairs.sortWith {
        case ((x0, y0), (x1, y1)) =>
          if (x0 < x1) true
          else if (x0 > x1) false
          else if (y0 <= y1) true
          else false
      }

      val n = xs.length
      val sumx = Array.fill(n + 1)(0L)
      val sumy = Array.fill(n + 1)(0L)

      def sum(i: Int, xs: List[Long], ys: List[Long]): Unit =
        if (i <= n) {
          sumx(i) = sumx(i - 1) + xs.head
          sumy(i) = sumy(i - 1) + ys.head
          sum(i + 1, xs.tail, ys.tail)
        }
      sum(1, xs, ys)

      def find(answer: Option[(Long, Long, Long)], pairs: List[(Long, Long)]): Option[(Long, Long, Long)] = pairs match {
        case Nil => answer
        case (x, y) :: tail =>
          val tx = xs.count(_ < x)
          val ty = ys.count(_ < y)

          val dx = (tx + 1) * x - sumx(tx + 1) + sumx(n) - sumx(tx + 1) - (n - tx - 1) * x
          val dy = (ty + 1) * y - sumy(ty + 1) + sumy(n) - sumy(ty + 1) - (n - ty - 1) * y
          val nanswer = answer match {
            case None => Some(x, y, dx + dy)
            case Some((x0, y0, d0)) if (d0 > dx + dy) => Some(x, y, dx + dy)
            case _ => answer
          }
          find(nanswer, tail)
      }

      find(None, pairs).map {
        case (x, y, d) => println(s"Case #$t: $x $y $d")
      }

      process(t + 1)
    }
  }
  process(1)
}