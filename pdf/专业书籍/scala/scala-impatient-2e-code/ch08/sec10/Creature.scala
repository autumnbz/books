// Compile without, then with -Xcheckinit

class Creature {
  val range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}

class Ant extends Creature {
  override val range = 2
}

class Bug extends {
  override val range = 3
} with Creature

class Cow extends Creature {
  override lazy val range = 4
}

object Main extends App {
  val a = new Ant
  println(a.range)
  println(a.env.length)

  val b = new Bug
  println(b.range)
  println(b.env.length)
}
