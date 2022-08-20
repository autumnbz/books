import java.time._
import scala.concurrent._
import scala.concurrent.duration._
import scala.math._
import scala.util._
import ExecutionContext.Implicits.global

object Main extends App {
  val f = Future {
    Thread.sleep(10000);
    if (random() < 0.5) throw new Exception
    42
  }

  Await.ready(f, 11.seconds)
  val Some(t) = f.value

  t match {
    case Success(v) => println(s"The answer is $v")
    case Failure(ex) => println(ex.getMessage)
  }

  if (t.isSuccess) println(s"The answer is ${t.get}")
  if (t.isFailure) println(t.failed.get.getMessage)

  def parseInt(str: String) = Try(str.toInt)

  val t1 = parseInt("42")
  println(t1)
  println(t1.toOption)
  val t2 = parseInt("two")
  println(t2)
  println(t2.toOption)
}
