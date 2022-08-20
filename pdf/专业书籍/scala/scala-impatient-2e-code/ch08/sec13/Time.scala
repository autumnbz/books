class MilTime private(val time: Int) extends AnyVal {
  def minutes = time % 100
  def hours = time / 100
  override def toString = f"$time04d"
}

object MilTime {
  def apply(t: Int) =
    if (0 <= t && t < 2400 && t % 100 < 60) new MilTime(t)
    else throw new IllegalArgumentException
}

object Main extends App {
  val lunch = MilTime(1230)
  println(lunch)
}
