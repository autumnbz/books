import scala.concurrent._
import scala.util._
import ExecutionContext.Implicits.global

object Main extends App {
  def getData(n: Int) = {
    for (i <- 1 to 10) {
      Thread.sleep(100);
      print(s"$n...")
    }
    val result = math.pow(10, n - 1).toInt * Random.nextInt(10)
    if (n == 2 && result < 50) throw new IllegalStateException("2")
    result
  }

  def getEx(delay: Int, ex: Exception) = {
    Thread.sleep(delay)
    throw ex
  }

  val future1 = Future { getData(1) }
  val future2 = Future { getData(2) }
  val future3 = Future { getData(3) }
  val future4 = Future { getData(4) }
  val futures = Set(future1, future2, future3, future4)
  println("sequence")
  val result = Future.sequence(futures)
  result.onComplete(r => println(s"\nResult: $r"))
  // Success(Set(...)) or Failure(IllegalStateException)  

  Thread.sleep(2000)

  println("sequence with exception")
  val result1 = Future.sequence(Set(
    Future { getData(1) },
    Future { getEx(600, new IllegalStateException("1")) },
    Future { getData(3) },
    Future { getEx(300, new IllegalStateException("4")) }))
      // won't be returned even though it came earlier
  result1.onComplete(r => println(s"\nResult: $r"))

  Thread.sleep(2000)

  println("traverse")
  val result2 = Future.traverse(1 to 4)(n => Future { getData(n) })
  result2.onComplete(r => println(s"Result: $r"))
  // Success(Vector(...)) or Failure(IllegalStateException)  

  Thread.sleep(2000)

  println("reduceLeft")
  val futures3 = (1 to 4).map(n => Future { getData(n) })
  val result3 = Future.reduceLeft(futures3)(_ + _)
  result3.onComplete(r => println(s"\nResult: $r"))
  // Success(...) or Failure(IllegalStateException)  

  Thread.sleep(2000)

  println("firstCompletedOf")
  val futures4 = (1 to 4).map(n => Future { getData(n) })
  val result4 = Future.firstCompletedOf(futures4)
  result4.onComplete(r => println(s"\nResult: $r"))
  // Success(...) or Failure(IllegalStateException)  

  Thread.sleep(2000)
  
  println("find")
  val futures5 = (1 to 4).map(n => Future { getData(n) })
  val result5 = Future.find(futures5)(n => n % 3 == 0)
  result5.onComplete(r => println(s"\nResult: $r"))  
  // Success(Some(...)), Success(None), or Failure(IllegalStateException)  

  Thread.sleep(2000)
}
