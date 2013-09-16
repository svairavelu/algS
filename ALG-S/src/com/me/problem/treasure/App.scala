package com.me.problem.treasure

import scala.collection.mutable.ListBuffer

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

      var kcs: Map[Int, Set[C]] = Map()

      val clb: ListBuffer[C] = new ListBuffer()
      for (i <- 1 to n) {
        val l = readLine
        val xs = l.split(" ")
        val kToOpen = (xs(0) toInt)
        val c = new C(i, kToOpen)
        clb += c

        kcs.get(kToOpen) match {
          case None => kcs += (kToOpen -> Set(c))
          case Some(s) => kcs += (kToOpen -> (s + c))
        }

        val kn = xs(1).toInt
        for (j <- 0 until kn) {
          val kGot = xs(j + 2).toInt
          c moreK (kGot)
        }
      }

      val cl = clb.toList

      def process(current: C, path: List[C], ks: Map[Int, Int], kcs: Map[Int, Set[C]]): (Map[Int, Int], List[C], Map[Int, Set[C]]) = {

        val nPath: ListBuffer[C] = new ListBuffer
        nPath ++= path
        nPath += current

        var keys = ks + ((current.kToOpen) -> (ks(current.kToOpen) - 1))
        var nkcs = kcs + (current.kToOpen -> (kcs(current.kToOpen) - current))

        current.keys.foreach(k => {
          keys.get(k) match {
            case None => keys += k -> 1
            case Some(n) => keys += k -> (n + 1)
          }
        })

        if (nPath.size == n) {
          (keys, nPath.toList, nkcs)
        } else {
          var usableKs = keys.filter(x => {
            val (k, n) = x
            n > 0 && nkcs.contains(k) && nkcs(k).size > 0
          }).map(_._1).toSet
          if (usableKs.size == 0) {
            (ks, path, kcs)
          } else {
            var found = false
            var pathC = nPath.toList
            for (c <- cl if !pathC.contains(c) && usableKs.contains(c.kToOpen) && !found) {
              val (pks, ppath, pkcs) = process(c, pathC, keys, nkcs)
              pathC = ppath
              keys = pks
              nkcs = pkcs

              if (ppath.size == n) {
                found = true
              }
              usableKs = keys.filter(x => {
                val (k, n) = x
                n > 0 && nkcs.contains(k) && nkcs(k).size > 0
              }).map(_._1).toSet
            }

            val possible = usableKs.size > 0

            if (found || possible) {
              (keys, pathC, nkcs)
            } else {
              (ks, path, kcs)
            }
          }
        }

      }

      var found = false
      var path: List[C] = Nil

      for (c <- cl if !found && keys.contains(c.kToOpen) && !path.contains(c)) {
        val (ks, ph, pkcs) = process(c, path, keys, kcs)

        path = ph
        keys = ks
        kcs = pkcs

        if (path.size == n) {
          found = true
        }
      }
      print(s"Case #$count:")
      if (!found) {
        println(" IMPOSSIBLE")
      } else {
        path.foreach(c => print(s" $c"))
        println
      }

      count += 1
      knl = readLine
    }

  }

}

class C(val label: Int, val kToOpen: Int) {

  private var ks: List[Int] = List()
  def moreK(k: Int) = ks = k :: ks

  def keys = ks

  var locked = true

  override def equals(other: Any) = other match {
    case that: C => this.label == that.label
    case _ => false
  }

  override def hashCode = this.label.hashCode

  override def toString = s"$label"
}