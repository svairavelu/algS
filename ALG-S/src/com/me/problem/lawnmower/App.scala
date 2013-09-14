package com.me.problem.lawnmower

object App extends App {

  def provideRow(m: Int) = {
    val row = readLine
    val a = row.split(" ").toArray
    a.map(_ toInt)
  }
  var tLine = readLine
  val t = tLine toInt
  var nmLine = readLine
  var count = 1
  while (nmLine != null) {
    val nm = nmLine.split(" ")
    val n = nm(0) toInt
    val m = nm(1) toInt
    val lawn = new Lawn(n, m)
    lawn.construct(provideRow)
    val yesNo = lawn.check
    if (yesNo) {
      println(s"Case #$count: YES")
    } else {
      println(s"Case #$count: NO")
    }

    count += 1
    nmLine = readLine
  }
}

class Lawn(val n: Int, val m: Int) {
  case class Cell(r: Int, c: Int, expectedHeight: Int, var height: Int)

  case class Mower(var height: Int)

  private var cells: Map[Int, Cell] = Map()

  private var maxRowHeights: Map[Int, Int] = Map()
  for (i <- 0 until n) {
    maxRowHeights += (i -> 0)
  }
  private var maxColHeights: Map[Int, Int] = Map()
  for (j <- 0 until m) {
    maxColHeights += (j -> 0)
  }

  type ProvideRow = Int => Array[Int]

  private val mower = Mower(0)

  def construct(f: ProvideRow) = {
    for (i <- 0 until n) {
      val row = f(m)
      var maxRowHeight = maxRowHeights(i)
      for (j <- 0 until m) {
        var maxColHeight = maxColHeights(j)
        val index = i * m + j
        val height = row(j)
        if (height > maxRowHeight) {
          maxRowHeight = height
        }
        if (height > maxColHeight) {
          maxColHeight = height
        }
        cells += index -> Cell(i, j, row(j), 100)
        maxColHeights += (j -> maxColHeight)
      }
      maxRowHeights += (i -> maxRowHeight)
    }
  }

  def check() = {

    def mow(i: Int, j: Int) = {
      val cell = cells(i * m + j)
      if(cell.height > mower.height) {
        cell.height = mower.height
      }
    }

    for (i <- 0 until n) {
      mower.height = maxRowHeights(i)
      for (j <- 0 until m) {
        mow(i, j)
      }
    }

    for {
      j <- 0 until m
    } {
      mower.height = maxColHeights(j)
      for (i <- 0 until n) {
        mow(i, j)
      }
    }

    val failed = cells.exists(x => {
      val (i, c) = x
      c.expectedHeight != c.height
    })

    !failed
  }
}