trait Reader[C] {
  def read(fileName: String): C
}

import scala.io._

class StringReader extends Reader[String] {
  def read(fileName: String) = Source.fromFile(fileName, "UTF-8").mkString
}

import java.awt.image._
import java.io._
import javax.imageio._

class ImageReader extends Reader[BufferedImage] {
  def read(fileName: String) = ImageIO.read(new File(fileName))
}
