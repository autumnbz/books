import java.io.File
import scala.io.Source
import scala.language.implicitConversions

object Main extends App {
  class RichFile(val from: File) {
    def read = Source.fromFile(from.getPath).mkString
  }

  implicit def file2RichFile(from: File) = new RichFile(from)

  val contents = new File("RichFile.scala").read
  println(contents)
}
