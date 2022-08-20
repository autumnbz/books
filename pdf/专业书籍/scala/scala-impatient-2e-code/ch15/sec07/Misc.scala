// scalac -deprecation Misc.scala

import scala.annotation.unchecked._
import java.awt.Font
import java.awt.Font._

object Misc {
  @deprecated(message = "Use factorial(n: BigInt) instead")
  def factorial(n: Int): Int = if (n <= 0) 1 else n * factorial(n - 1)
    // Recursive call causes a deprecation warning 
  def draw(@deprecatedName('sz) size: Int, style: Int = PLAIN) {
    val font = new Font("Dialog", style, size)
    // ...
  }

  draw(sz = 12)

  def process(lst: List[Int]) = 
    (lst: @unchecked) match {
      case head :: tail => head + tail.length
    }
}

trait Comparable[-T] extends 
  java.lang.Comparable[T @uncheckedVariance]

