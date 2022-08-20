import java.time._
import scala.concurrent._
import scala.concurrent.duration._
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
  val combined = future1.flatMap(n1 => future2.map(n2 => n1 + n2))

  Thread.sleep(3000)
  println(combined)
}
