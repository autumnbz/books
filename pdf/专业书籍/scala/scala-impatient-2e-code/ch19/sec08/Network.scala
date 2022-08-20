import scala.language.existentials
import scala.collection.mutable.ArrayBuffer

class Network {
  class Member(val name: String) {
    val contacts = new ArrayBuffer[Network#Member]
  }

  private val members = new ArrayBuffer[Member]

  def join(name: String) = {
    val m = new Member(name)
    members += m
    m
  }
}

object Main extends App {
  def process[M <: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)

  val chatter = new Network
  val myFace = new Network
  val fred = chatter.join("Fred")
  val wilma = chatter.join("Wilma")
  val barney = myFace.join("Barney")
  process(fred, wilma) // Ok
  process(fred, barney) // Error
}
