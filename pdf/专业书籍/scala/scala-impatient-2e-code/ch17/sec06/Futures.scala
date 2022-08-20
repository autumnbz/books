\import java.sql._
import scala.concurrent._
import scala.util._
import ExecutionContext.Implicits.global

object Main extends App {
  def getData1() = {
    for (i <- 1 to 10) {
      Thread.sleep(100);
      print("1...")
    }
    val result = Random.nextInt(10)
    println(result)
    result
  }

  def getData2() = {
    for (i <- 1 to 10) {
      Thread.sleep(100);
      print("2...")
    }
    val result = 10 * Random.nextInt(10)
    println(result)
    result
  }

  val future1 = Future { getData1() }
  val future2 = Future { getData2() }
  val combined = for (n1 <- future1; n2 <- future2) yield n1 + n2
  combined.foreach(n => println(s"Result: $n"))

  val data = "Data"
  def persist(data: String): Int = if (math.random() > 0) throw new Exception else 1
  val f = Future { persist(data) } recover { case e: SQLException => 0 }
  for (ex <- f.failed) println(ex)

  val future3 = Future { getData1() }
  val future4 = Future { getData2() }
  val combined2 = future3.zipWith(future4)(_ + _)
  combined2.foreach(n => println(s"Result: $n"))

  Thread.sleep(5000)
}
