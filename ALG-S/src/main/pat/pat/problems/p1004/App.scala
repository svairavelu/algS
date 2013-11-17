package pat.problems.p1004

object App extends App {

  val firstLine = readLine
  val nm = firstLine.split(" ").map(_ toInt)
  
  val n = nm(0)
  val m = nm(1)
  
  val fms = new Array[FM](n)
  
  fms(0) = new FM(1)
  
  var maxLevel = 0
  for(i <- 0 until m) {
    val line = readLine.split(" ").map(_ toInt)
    val id = line(0)
    val k = line(1)
    val fmp = fms(id - 1)
    require(fmp != null)
    fmp.hasChildren = true
    for(j <- 0 until k) {
      val idj = line(j + 2)
      fms(idj - 1) = new FM(idj, fmp.level + 1)
      if(fmp.level + 1 > maxLevel) {
        maxLevel = fmp.level + 1
      }
    }
  }
  
  val sorted = fms.sortWith((x, y) => {
    x.level < y.level
  })
  
  val levelCount = new Array[Int](maxLevel + 1)
  for(i <- 0 to maxLevel) {
    levelCount(i) = 0
  }
  var level = 0
  sorted.foreach(x => {
    if(x.level > level) {
      level = x.level
    }
    
    if(x.hasChildren == false) {
    	levelCount(level) += 1
    }
  })
  
  println(levelCount.toList.mkString(" "))
}

class FM(val lable: Int, val level: Int = 0) {
	var hasChildren = false
	override def toString = s"$lable"
}