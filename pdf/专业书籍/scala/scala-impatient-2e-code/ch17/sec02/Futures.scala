import java.time._
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

object Main extends App {
  val f = Future { Thread.sleep(10000); 42 }
  val result = Await.result(f, 11.seconds)
  println(result)

  val f2 = Future { Thread.sleep(10000); 42 }
  Await.ready(f2, 11.seconds)
  val Some(t) = f2.value
  println(t)
}

