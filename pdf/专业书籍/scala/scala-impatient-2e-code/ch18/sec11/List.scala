abstract class List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Node[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

object Empty extends List[Nothing] {
// It can't be object Empty[T] extends List[T] 
// OK to be class Empty[T] extends List[T] 
  def isEmpty = true
  def head = throw new UnsupportedOperationException
  def tail = throw new UnsupportedOperationException
}

object Main extends App {
  def show[T](lst: List[T]) {
    if (!lst.isEmpty) { println(lst.head); show(lst.tail) }
  }

  val lst = new Node(42, Empty)
  show(new Node(1729, lst))
}
