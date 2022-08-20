import scala.math._
import scala.reflect._

trait Iterator[E] {
  def next(): E
  def hasNext: Boolean
}

trait Builder[-E, +To] {
  def +=(e: E): Unit
  def result(): To
}

trait CanBuildFrom[-From, -E, +To] {
  def apply(): Builder[E, To]
}

trait Iterable[A, Repr] {
  def iterator(): Iterator[A]

  def map[B, That](f : (A) => B)(implicit bf: CanBuildFrom[Repr, B, That]): That = {
    val builder = bf()
    val iter = iterator()
    while (iter.hasNext) builder += f(iter.next())
    builder.result
  }
}

class Buffer[E : ClassTag] extends Iterable[E, Buffer[E]] 
    with Builder[E, Buffer[E]] {
  private var capacity = 10
  private var length = 0
  private var elems = new Array[E](capacity) 
  def iterator() = new Iterator[E] {
    private var i = 0
    def hasNext = i < length
    def next() = { i += 1; elems(i - 1) }
  }
  def +=(e: E) {
    if (length == capacity) {
      capacity = 2 * capacity
      val nelems = new Array[E](capacity) 
      for (i <- 0 until length) nelems(i) = elems(i)
      elems = nelems
    }
    elems(length) = e
    length += 1
  }
  def result() = this
}

object Buffer {
  implicit def canBuildFrom[E : ClassTag] = new CanBuildFrom[Buffer[_], E, Buffer[E]] {
    def apply() = new Buffer[E]
  }
}

class Range(val low: Int, val high: Int) extends Iterable[Int, Range] {
  def iterator() = new Iterator[Int] {
    private var i = low
    def hasNext = i <= high
    def next() = { i += 1; i - 1 }
  }  
}

object Range {
  implicit def canBuildFrom[E : ClassTag] = new CanBuildFrom[Range, E, Buffer[E]] {
    def apply() = new Buffer[E]
  }
}

object Main extends App {
  val names = new Buffer[String]
  names += "Fred"
  names += "Linda"
  val lengths = names.map(_.length)
  lengths.map(println(_))
  
  val res = new Range(1, 10).map(sqrt(_))
  res.map(println(_))
}
