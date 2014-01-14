package fp

object day3 extends App {

  import errorhandling._

  //  case class Employee(name: String, department: String)
  //  val employeesByName: Map[String, Employee] =
  //    List(Employee("Alice", "R&D"), Employee("Bob", "Accounting")).
  //      map(e => (e.name, e)).toMap
  //  val dept: Option[String] = employeesByName.get("Joe").map(_.department)

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))
  }

  import java.util.regex._
  def pattern(s: String): Option[Pattern] =
    try {
      Some(Pattern.compile(s))
    } catch {
      case e: PatternSyntaxException => None
    }

  def mkMatcher(pat: String): Option[String => Boolean] =
    pattern(pat) map (p => (s: String) => p.matcher(s).matches)

  def mkMatcher_1(pat: String): Option[String => Boolean] =
    for {
      p <- pattern(pat)
    } yield ((s: String) => p.matcher(s).matches)

  def doesMatch(pat: String, s: String): Option[Boolean] =
    for {
      p <- mkMatcher_1(pat)
    } yield p(s)

  def bothMatch(pat: String, pat2: String, s: String): Option[Boolean] =
    for {
      f <- mkMatcher(pat)
      g <- mkMatcher(pat2)
    } yield f(s) && g(s)

  def bothMatch_1(pat: String, pat2: String, s: String): Option[Boolean] =
    mkMatcher(pat) flatMap (f =>
      mkMatcher(pat2) map (g =>
        f(s) && g(s)))

  import Option._
  def bothMatch_2(pat1: String, pat2: String, s: String): Option[Boolean] = {
    val a = mkMatcher(pat1)
    val b = mkMatcher(pat2)
    map2(a, b)((f, g) => f(s) && g(s))
  }
}