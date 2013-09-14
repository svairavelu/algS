package com.me.problem.beautifulstring

import scala.collection.immutable.BitSet
import scala.collection.immutable.SortedMap

object BeautifulString extends App {

  Iterator.continually(readLine).foreach((line) => {
        var s = new S
        var theMostBeautifulString = s.process(line)
        println(theMostBeautifulString)
//    val sw = new SW(line)
//    println(sw.theMostBeautifulString)
  })
}

//class SW(val words: String) {
//
//  case class W(val w: String, val index: Int) {
//    override def toString = w
//  }
//
//  private def printMap() {
//    print((for (i <- 0 until wmap.size) yield wmap(i)).mkString(""))
//  }
//
//  private var wmap: Map[Int, W] = SortedMap()
//
//  for (i <- 0 until 26) {
//    val target = ('z' - i).toChar.toString
//
//    var index = wmap.size - 1
//    var found = false
//    while (!found && index > -2) {
//      val start = if (index == -1) 0 else wmap(index).index
//
//      val indexAfterStartOfTarget = words.indexOf(target, start)
//      if (indexAfterStartOfTarget >= start) {
//        found = true
//        val nxtIdx = index + 1
//        if (wmap.size > nxtIdx) {
//          wmap = wmap.map(iw => {
//            val (i, w) = iw
//            if (i >= nxtIdx) {
//              (i + 1) -> w
//            } else {
//              i -> w
//            }
//          })
//        }
//        wmap += nxtIdx -> new W(target, indexAfterStartOfTarget)
//        print(s"$target\t")
//        printMap
//        println
//      }
//      index -= 1
//    }
//  }
//
//  val theMostBeautifulString = (for (i <- 0 until wmap.size) yield (wmap(i))) mkString ""
//}

class S {
  private var wmap: Map[Int, String] = SortedMap()
  private var count = 0

  private def printMap() {
    print((for (i <- 0 until wmap.size) yield wmap(i)).mkString(""))
  }

  def process(line: String) = {
    val chars = line.toArray
    for (k <- 0 until chars.length) {
      val char = chars(k)
      val s = char toString
      val lastIndex = wmap.size - 1

      val found = wmap.find(_._2 == s)

      found match {
        case Some(x) =>
          x match {
            case (`lastIndex`, w) =>
            //no need to move
            case (i, w) =>
              var found = false
              var firstGt = i
              (for (j <- i to lastIndex if !found) {
                if (wmap(j) > w) {
                  found = true
                  firstGt = j
                }
              })
              val gw = if (found) Some(firstGt, wmap(firstGt)) else None

              gw match {
                case Some(y) =>
                  val (i1, w1) = y
                  if (i1 == i + 1) {
                    wmap -= i
                    wmap = wmap.map(x => {
                      val (j, y) = x
                      if (i < j) {
                        (j - 1) -> y
                      } else {
                        j -> y
                      }
                    })
                    wmap += (lastIndex -> w)
                  } else {
                    var repeat = true
                    for (j <- (i + 1) until i1 if repeat) {
                      val y = wmap(j)
                      val repeatedIndex = line.indexOf(y, k)
                      repeat = repeatedIndex > k
                    }
                    if (repeat) {
                      wmap -= i
                      wmap = wmap.map(x => {
                        val (j, y) = x
                        if (j > i) {
                          (j - 1) -> y
                        } else {
                          j -> y
                        }
                      })
                      wmap += (lastIndex -> w)
                    }
                  }

                case None =>
              }

          }
        case None =>
          wmap += ((lastIndex + 1) -> s)
          count += 1
      }
    }
    (for (i <- 0 until wmap.size) yield wmap(i)).mkString("")
  }
}
