import java.io.File
import scala.io.Source

object Main extends App {
  implicit class RichFile(val from: File) extends AnyVal {
    def read = Source.fromFile(from.getPath).mkString
  }

  val contents = new File("RichFile2.scala").read
  println(contents)
}
