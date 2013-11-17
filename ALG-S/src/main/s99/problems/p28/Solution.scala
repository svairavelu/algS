/**
 *
 */
package problems.p28

/**
 * @author Blues
 *
 */
object Solution {

  def pack[A](ls: List[A]): List[List[A]] = {
    if (ls.isEmpty) List(List())
    else {
      val (packed, next) = ls span { _ == ls.head }
      if (next == Nil) List(packed)
      else packed :: pack(next)
    }
  }

  def encode[A](ls: List[A]): List[(Int, A)] =
    pack(ls) map { e => (e.length, e.head) }

  def lsort[A](ls: List[List[A]]): List[List[A]] =
    ls sortWith { _.length < _.length }

  def lsortFreq[A](ls: List[List[A]]): List[List[A]] = {
    val freqs = Map(encode(ls map { _.length } sortWith { _ < _ }) map { _.swap }: _*)
    ls sortWith { (e1, e2) => freqs(e1.length) < freqs(e2.length) }
  }

  def main(args: Array[String]): Unit = {
    println(lsortFreq(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o))).mkString(", "))
  }
}