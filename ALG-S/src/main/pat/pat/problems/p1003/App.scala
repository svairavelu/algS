package pat.problems.p1003

object App extends App {

  val firstLine = readLine
  val nmc1c2 = firstLine.split(" ")
  val n = nmc1c2(0) toInt
  val m = nmc1c2(1) toInt
  val c1 = nmc1c2(2) toInt
  val c2 = nmc1c2(3) toInt

  val secondLine = readLine
  val mrcs = secondLine.split(" ").map(_ toInt)

  val g = new Array[Array[Int]](n)
  for (i <- 0 until n) {
    g(i) = new Array[Int](n)
    for (j <- 0 until n) {
      g(i)(j) = 0
    }
  }

  for (i <- 0 until m) {
    val line = readLine
    val nmd = line.split(" ").map(_ toInt)
    val n = nmd(0)
    val m = nmd(1)
    val d = nmd(2)
    g(n)(m) = d
  }

  val alg = new Alg(g, mrcs, n, c1, c2)
  alg.rt match {
    case Some(x) => println(s"${x._1} ${x._2}")
    case None => println("Something wrong!")
  }
}

class Alg(g: Array[Array[Int]], mrcs: Array[Int], n: Int, start: Int, end: Int) {
  case class E(v: Int, w: Int, var d: Int = Int.MaxValue) {
    var index: Int = -1
    var sc = 0
    var mrc = 0
  }

  class PH(n: Int) {
    private var currentPos = 0
    private val array = new Array[E](n + 1)

    implicit def e2Int(e: E) = e.d

    def deleteMin = {
      if (currentPos > 0) {
        val min = array(1)
        val last = array(currentPos)
        var temp = 1
        array(temp) = last
        var left = 2 * temp
        var right = left + 1

        while (left <= currentPos && array(left) < last) {
          if (array(right) < array(left)) {
            array(temp) = array(right)
            temp = right
          } else {
            array(temp) = array(left)
            temp = left
          }
          left = 2 * temp
          right = left + 1
        }

        array(temp) = last
        currentPos -= 1
        min.index = -1
        Some(min)
      } else {
        None
      }
    }

    def insert(e: E) = {
      currentPos += 1
      require(currentPos <= n)
      var temp = currentPos
      var parent = temp / 2
      while (parent > 0 && array(parent) > e.d) {
        array(temp) = array(parent)
        temp = parent
        parent = temp / 2
      }

      array(temp) = e
      e.index = temp
    }

    def change(e: E, vo: Int, vn: Int) = {
      if (vn < vo) {
        //percolate up
        var temp = e.index
        var parent = temp / 2
        while (parent > 0 && array(parent) > vn) {
          array(temp) = array(parent)
          temp = parent
          parent = temp / 2
        }
        array(temp) = e
        e.index = temp
        true
      } else {
        false
      }
    }

    def isEmpty = currentPos == 0
  }

  val path = new Array[E](n)

  val distance = new Array[Int](n)
  for (i <- 0 until n) {
    if (i != start) {
      distance(i) = Int.MaxValue
    } else {
      distance(i) = 0
    }
  }
  val flags = new Array[Boolean](n)
  for (i <- 0 until n) {
    flags(i) = false
  }

  val ph = new PH(n)

  val eStart = E(start, start, 0)
  eStart.mrc = mrcs(start)

  ph.insert(eStart)

  var done = false
  var eEnd: E = null
  while (!ph.isEmpty && !done) {
    val min = ph.deleteMin.get
    val v = min.w
    flags(v) = true
    if (v == end) {
      done = true
      eEnd = min
    } else {
      val row = g(v)
      val dv = distance(v)
      for {
        w <- 0 until row.length
        d = row(w)
        if (d > 0 && !flags(w))
      } {
        val dw = distance(w)
        if (dw > dv + d) {
          var e = path(w)
          if (e == null) {
            e = E(v, w, distance(w))
            e.sc = 1
            e.mrc = min.mrc + mrcs(w)
            ph.insert(e)
            path(w) = e
          } else {
            ph.change(e, dw, dv + d)
            e.d = dv + d
          }
          distance(w) = dv + d
        } else if (dw == dv + d) {
          val e = path(w)
          e.sc += 1
          val nmrc = min.mrc + mrcs(w)
          if (nmrc > e.mrc) {
            e.mrc = nmrc
          }
        }
      }
    }
  }

  val rt = if (done) {
    Some((eEnd.sc, eEnd.mrc))
  } else {
    None
  }
}