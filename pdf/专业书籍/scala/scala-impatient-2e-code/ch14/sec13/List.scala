abstract class List {
  def ::(a: Any) = new ::(a, this)
}

case object Nil extends List 
case class ::(head: Any, tail: List) extends List 

object Main extends App {
  def show(lst: List) {
    lst match {
      case ::(h, t) => { println(h); show(t) }
      case Nil => 
    }
  }

  val friends = "Fred" :: "Wilma" :: "Barney" :: Nil
  println(friends)
  println(friends == "Fred" :: "Wilma" :: "Barney" :: Nil)
  println(friends == "Fred" :: "Barney" :: "Wilma" :: Nil)
  show(friends)
}
