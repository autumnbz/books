import java.time._
import scala.concurrent._
import ExecutionContext.Implicits.global

object Main extends App {
  Future {
    Thread.sleep(10000)
    println(s"This is the future at ${LocalTime.now}")
  }
  println(s"This is the present at ${LocalTime.now}")

  Thread.sleep(11000)

  Future { for (i <- 1 to 100) { print("A"); Thread.sleep(10) } }
  Future { for (i <- 1 to 100) { print("B"); Thread.sleep(10) } }

  Thread.sleep(2000)

  val f = Future {
    Thread.sleep(10000)
    42
  }
  println(f)

  Thread.sleep(11000)

  println(f)

  val f2 = Future {
    if (LocalTime.now.getHour > 12)
      throw new Exception("too late")
    42
  }
  Thread.sleep(1000)
  println(f2)
}

