package com.me.problem.tictactoetomek

import scala.collection.mutable.{ BitSet, ListBuffer, Map }

object App extends App {

  var tl = readLine
  while (tl != null) {

    val t = tl toInt

    var result = "T"
    for (i <- 0 until t) {
      var won = false
      val game = new Game
      for (j <- 0 until 4) {
        val row = readLine
        if (!won) {
          result = game.append((row.toCharArray()).map(_ toString))
          result match {
            case "X" =>
              won = true
            case "O" =>
              won = true
            case _ =>
              won = false
          }
        }
      }

      result match {
        case "N" => println(s"Case #${i + 1}: Game has not completed")
        case "D" => println(s"Case #${i + 1}: Draw")
        case _ => println(s"Case #${i + 1}: $result won")
      }
      readLine
    }

    tl = readLine
  }

}

class Game {
  private val checkPoints: Map[Int, Int] = Map()
  for (i <- 0 until 10) {
    checkPoints += (i -> 0)
  }
  private val board: ListBuffer[Array[String]] = ListBuffer()
  private var currentRow = -1;

  def append(row: Array[String]) = {
    currentRow += 1
    board += row

    val rowCheck = 2 * currentRow
    for (col <- 0 until 4) {
      val y = row(col)

      if (y != ".") {
        val colCheck = col * 2 + 1

        if (currentRow > 0) {
          if (checkPoints(colCheck) >= 0) {
            val theOne = findC(currentRow - 1, col)
            if (compare(y, theOne)) {
              checkPoints(colCheck) += 1
            } else {
              checkPoints(colCheck) = -1 //no need to check again
            }
          }

          if (currentRow == col) {
            if (checkPoints(8) >= 0) {
              val theOne = findA(currentRow - 1, col - 1)
              if (compare(y, theOne)) {
                checkPoints(8) += 1
              } else {
                checkPoints(8) = -1
              }
            }
          }

          if (currentRow + col == 3) {
            if (checkPoints(9) >= 0) {
              val theOne = findB(currentRow - 1, col + 1)
              if (compare(y, theOne)) {
                checkPoints(9) += 1
              } else {
                checkPoints(9) = -1
              }
            }
          }
        } else {
          checkPoints(colCheck) += 1
          if (col == 0) {
            checkPoints(8) += 1
          } else if (col == 3) {
            checkPoints(9) += 1
          }
        }

        if (col > 0) {
          val theOne = findD(currentRow, col - 1)
          if (compare(y, theOne)) {
            checkPoints(rowCheck) += 1
          } else {
            checkPoints(rowCheck) = -1
          }
        } else {
          checkPoints(rowCheck) += 1
        }
      }
    }

    if (currentRow < 3) {
      if (checkPoints(rowCheck) == 4) {
        findWinner(rowCheck)
      } else {
        "N"
      }
    } else {
      var found = false
      var theWinner = ""
      for (i <- 0 until 10 if !found) {
        if (checkPoints(i) == 4) {
          found = true
          theWinner = findWinner(i)
        }
      }

      if (found) {
        theWinner
      } else {
        val list = board.toList.flatten
        if (list.exists(_ == ".")) "N" else "D"
      }
    }
  }

  private def find(row: Int, col: Int, next: (Int, Int) => (Int, Int), stop: (Int, Int) => Boolean): String = {
    val theRow = board(row)
    val theOne = theRow(col)
    if (theOne != "T" || stop(row, col)) {
      theOne
    } else {
      val (r, c) = next(row, col)
      find(r, c, next, stop)
    }
  }

  private def findA(row: Int, col: Int): String = {
    find(row, col, (r, c) => (r - 1, c - 1), (r, c) => (r == 0))
  }

  private def findB(row: Int, col: Int): String = {
    find(row, col, (r, c) => (r -1, c + 1), (r, c) => r == 0)
  }

  private def findC(row: Int, col: Int): String = {
    find(row, col, (r, c) => (r - 1, c), (r, c) => r == 0)
  }

  private def findD(row: Int, col: Int): String = {
    find(row, col, (r, c) => (r, c - 1), (r, c) => c == 0)
  }

  private def compare(a: String, b: String): Boolean = {
    if (a == "." || b == ".") {
      false
    } else {
      if (a == b) {
        true
      } else if (a == "T" || b == "T") {
        true
      } else {
        false
      }
    }

  }

  private def findWinner(index: Int) = {
    val list = board.toList.flatten

    if (index == 8) {
      var row = 0
      var col = 0
      var i = row * 4 + col
      while (list(i) == "T") {
        row = (row + 1) % 4
        col = (col + 1) % 4
        i = row * 4 + col
      }
      list(i)
    } else if (index == 9) {
      var row = 0
      var col = 3 - row
      var i = row * 4 + col
      while (list(i) == "T") {
        row = (row + 1) % 4
        col = 3 - row
        i = row * 4 + col
      }
      list(i)

    } else if (index % 2 == 0) {
      var row = index / 2
      var col = 0
      var i = row * 4 + col
      while (list(i) == "T") {
        col += 1
        i = row * 4 + col
      }
      list(i)
    } else {
      var col = index / 2
      var row = 0
      var i = row * 4 + col
      while (list(i) == "T") {
        row += 1
        i = row * 4 + col
      }
      list(i)
    }
  }
}

