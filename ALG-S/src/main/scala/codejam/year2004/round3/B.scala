package codejam.year2004.round3

import scala.io.Source
import scala.concurrent.ExecutionContext
import scala.concurrent.future
import scala.actors.threadpool.Executors

object B extends App {

  val file = "src/main/scala/codejam/year2004/round3/B-large-practice.in"

  val lines = Source.fromFile(file).getLines()

  val T = lines.next().toInt

//  implicit val ec = new ExecutionContext {
//    val threadPool = Executors.newFixedThreadPool(4);
//
//    def execute(runnable: Runnable) {
//      threadPool.submit(runnable)
//    }
//
//    def reportFailure(t: Throwable) {}
//  }

  def cal(p: Int, q: Int, n: Int): Int = {
    val ms = Array.fill(n, 2)(0)

    ms.foldLeft(0)((i, x) => {
      val hg = lines.next.split("\\s+").map(_.toInt)
      ms(i)(0) = hg(0)
      ms(i)(1) = hg(1)
      i + 1
    })

    var cache = Map.empty[(Int, Int, Int), Int]
    def play(i: Int, rem: Int, extra: Int): Int =
      if (cache.contains((i, rem, extra))) cache((i, rem, extra))
      else {
        val v =
          if (rem <= 0 && i == n - 1) 0
          else if (rem <= 0) play(i + 1, ms(i + 1)(0), extra)
          else {
            val letTower = play(i, rem - q, extra + 1)
            if (extra > 0) {
              letTower max (if (rem <= p) ms(i)(1) else 0) + play(i, rem - p, extra - 1)
            } else letTower
          }
        cache += (i, rem, extra) -> v
        v
      }

    play(0, ms(0)(0), 1)
  }

  val fs = (1 to T).toList.map(t => {
    val line = lines.next.split("\\s+").map(_.toInt)
    (t, cal(line(0), line(1), line(2)))
  })

  fs.foreach {
    case (t, x) => println(s"Case #$t: $x")
  }
}