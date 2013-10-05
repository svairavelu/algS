package com.me.problem.treasure

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Queue

object App extends App {

  var count = 1
  val tl = readLine
  if (tl != null) {
    val t = tl toInt
    var knl = readLine
    while (knl != null) {
      val kn = knl split (" ")
      val k = kn(0) toInt
      val n = kn(1) toInt

      var keys: Map[Int, Int] = Map()

      val startKs = readLine().split(" ")

      for (i <- 0 until k) {
        val tp = startKs(i) toInt

        keys.get(tp) match {
          case Some(k) =>
            keys += tp -> (k + 1)
          case None =>
            keys += tp -> 1
        }

      }

      val clb: ListBuffer[C] = new ListBuffer()
      for (i <- 1 to n) {
        val l = readLine
        val xs = l.split(" ")
        val kToOpen = (xs(0) toInt)
        val c = new C(i, kToOpen)
        clb += c

        val kn = xs(1).toInt
        for (j <- 0 until kn) {
          val kGot = xs(j + 2).toInt
          c moreK (kGot)
        }
      }

      val cl = clb.toList

      def rule1(keys: Map[Int, Int], chests: List[C]): Boolean = {
        var nkeys = keys
        var cmap = Map.empty[Int, Int]
        chests foreach (c => {
          cmap get (c.tp) match {
            case Some(count) => cmap += (c.tp) -> (count + 1)
            case None => cmap += (c.tp) -> 1
          }

          c.ks foreach (k => {
            nkeys get (k) match {
              case Some(count) => nkeys += k -> (count + 1)
              case None => nkeys += k -> 1
            }
          })
        })

        var valid = true
        for ((k, count) <- nkeys if valid) {
          cmap get (k) match {
            case Some(ccount) => valid = count >= ccount
            case None =>
          }
        }
        valid
      }

      val totalCount = cl.size
      def find(cl: List[C], path: List[C], keys: Map[Int, Int]): List[C] = {

        cl foreach (_.visited = false)

        def rule2(chests: List[C], keys: Map[Int, Int]) = {
          val queueToProcess = Queue.empty[C]

          def check(c: C): Boolean = {
            c.visited = true

            for (xc <- cl if !xc.visited) {
              if (c.ks.contains(xc.tp)) {
                queueToProcess.enqueue(xc)
              }
            }

            if (queueToProcess.size == 0) {
              val notProcessedCount = (0 /: cl)(
                (count, c) => {
                  if (c.visited) {
                    count
                  } else {
                    count + 1
                  }
                })

              if (notProcessedCount == 0) {
                true
              } else {
                false
              }
            } else {
              val nextC = queueToProcess.dequeue
              check(nextC)
            }
          }

          var valid = false

          for (c <- chests if !valid) {
            keys get (c.tp) match {
              case Some(count) => if (count > 0) valid = check(c)
              case None =>
            }
          }

          valid
        }

        def blackbox(start: C, cpath: List[C], ks: Map[Int, Int]): List[C] = {
          val ncpath = start :: cpath

          if (ncpath.size == totalCount) {
            List(start)
          } else {
            var nks = ks
            start.ks foreach (k => {
              nks get (k) match {
                case Some(count) => nks += k -> (count + 1)
                case None => nks += k -> 1
              }
            })

            nks += (start.tp) -> (nks(start.tp) - 1)
            val ncl = cl.filter(!ncpath.contains(_))
            val rt = find(ncl, ncpath, nks)
            rt match {
              case Nil => Nil
              case list => start :: list
            }
          }
        }

        if (rule1(keys, cl) && rule2(cl, keys)) {
          var found = false
          var p = List.empty[C]
          for (c <- cl if !found) {
            keys get (c.tp) match {
              case Some(count) =>
                if (count > 0) {
                  p = blackbox(c, path, keys)
                  p match {
                    case Nil =>
                    case p => found = true
                  }
                }
              case None =>
            }

          }

          if (found) {
            p
          } else {
            Nil
          }
        } else {
          Nil
        }
      }

      find(cl, List.empty[C], keys) match {
        case Nil => println(s"Case #$count: IMPOSSIBLE")
        case path => println(s"Case #$count: ${path.mkString(" ")}")
      }

      count += 1
      knl = readLine
    }

  }

}

class C(val label: Int, val tp: Int) {

  var ks = List.empty[Int]

  def moreK(k: Int) = ks = k :: ks

  var visited = false

  override def equals(other: Any) = other match {
    case that: C => this.label == that.label
    case _ => false
  }

  override def hashCode = this.label.hashCode

  override def toString = s"$label"
}