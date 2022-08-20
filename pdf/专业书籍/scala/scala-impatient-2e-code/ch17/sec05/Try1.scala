import scala.util._
import scala.io._

object Main extends App {
  def readInt(prompt: String) = Try(StdIn.readLine(s"$prompt: ").toInt)
  val t = for (n1 <- readInt("n1"); n2 <- readInt("n2")) yield n1 + n2
  println(t)
}
