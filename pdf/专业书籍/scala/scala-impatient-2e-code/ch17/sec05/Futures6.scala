import java.time._
import scala.concurrent._
import scala.concurrent.duration._
import scala.util._
import ExecutionContext.Implicits.global

object Main extends App {
  def getData() = {
    for (i <- 1 to 10) {
      Thread.sleep(100);
      print("1...")
    }
    val result = Random.nextInt(10)
    println(result)
    result
  }

  def getMoreData(arg: Int) = {
    for (i <- 1 to 10) {
      Thread.sleep(100);
      print("2...")
    }
    val result = 10 * Random.nextInt(arg + 1)
    println(result)
    result
  }

  def future1 = Future { getData() }
  def future2(arg: Int) = Future { getMoreData(arg) }
  val combined = for (n1 <- future1; n2 <- future2(n1)) yield n1 + n2

  Thread.sleep(3000)
  println(combined)
}
