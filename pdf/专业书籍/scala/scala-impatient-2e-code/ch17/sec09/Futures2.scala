import java.util.concurrent.Executors
import scala.concurrent._
import scala.io._

object Main extends App {
  val pool = Executors.newCachedThreadPool()
  implicit val ec = ExecutionContext.fromExecutor(pool)

  val f = Future {
    val url = "http://horstmann.com/index.html"
    blocking {
      val contents = Source.fromURL(url).mkString
      contents.substring(0, 300) + "..."
    }
  }

  println(pool)

  f.foreach(r => println(r))

  Thread.sleep(1000)
  pool.shutdown()
}
