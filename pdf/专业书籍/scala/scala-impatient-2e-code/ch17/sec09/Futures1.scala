import scala.concurrent._
import scala.io._
import ExecutionContext.Implicits.global

object Main extends App {
  val f = Future {
    val url = "http://horstmann.com/index.html"
    blocking {
      val contents = Source.fromURL(url).mkString
      contents.substring(0, 300) + "..."
    }
  }

  f.foreach(r => println(r))

  Thread.sleep(1000)
}
