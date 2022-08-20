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

  def workHard(arg: String) = {
    println("Working hard")
    Thread.sleep(1000)
    arg.length
  }

  def workOnSomethingElse() = {
    Thread.sleep(1000)
    println("Working on something else")
    Thread.sleep(1000)
    0
  }

  def workSmart(arg: String) = {
    println("Working smart")
    Thread.sleep(100)
    42
  }


  def computeAnswer(arg: String) = {
    val p = Promise[Int]()
    Future {
      val n = workHard(arg)
      p.success(n)
      workOnSomethingElse()
    }
    p.future
  }

  val f = computeAnswer("Fred")
  f.foreach(n => println(s"Result: $n"))

  Thread.sleep(3000)

  val p1 = Promise[Int]()
  val p2 = Promise[Int]()
  Future {
    val n1 = getData1()
    p1.success(n1)
    val n2 = getData2()
    p2.success(n2)
  }

  p1.future.foreach(n => println(s"Result 1: $n"))
  p2.future.foreach(n => println(s"Result 2: $n"))

  Thread.sleep(3000)

  val p = Promise[Int]()
  val arg = "Fred"
  Future {
    var n = workHard(arg)
    p.trySuccess(n)
  }
  Future {
    var n = workSmart(arg)
    p.trySuccess(n)
  }

  p.future.foreach(n => println(s"Result: $n"))

  Thread.sleep(3000)
}
