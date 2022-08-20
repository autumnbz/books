import java.time._
import scala.concurrent._
import scala.math._
import scala.util._
import ExecutionContext.Implicits.global

object Main extends App {
  val f = Future {
    Thread.sleep(10000);
    if (random() < 0.5) throw new Exception
    42
  }

  f.onComplete {
    case Success(v) => println(s"The answer is $v")
    case Failure(ex) => println(ex.getMessage)
  }
}
