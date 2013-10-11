package pat.problems.p1005

object App extends App {

  val words = Array("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

  val line = readLine
  val digits = line.toCharArray.map(_ - '0')
  //  println(digits.mkString(" "))
  val sum = (0 /: digits)(_ + _)

  var result = List.empty[String]
  var temp = sum
  if (temp != 0) {
    while (temp > 0) {
      val left = temp % 10
      result = words(left) :: result
      temp = temp / 10
    }
  } else {
    result = words(0) :: result
  }
  println(result.mkString(" "))
}