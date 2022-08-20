trait Logger {
  def log(msg: String) // This method is abstract
}

trait TimestampLogger extends Logger {
  abstract override def log(msg: String) {
    super.log(new java.util.Date() + " " + msg)
  }
}

trait ShortLogger extends Logger {
  abstract override def log(msg: String) {
    super.log(
      if (msg.length <= 15) msg else s"${msg.substring(0, 12)}...")
  }
}

trait ConsoleLogger extends Logger {
  override def log(msg: String) { println(msg) }
}

class Account {
  protected var balance = 0.0
}

abstract class SavingsAccount extends Account with Logger {
  def withdraw(amount: Double) {
    if (amount > balance) log("Insufficient funds")
    else balance -= amount
  }

  // More methods ...
}

object Main extends App {
  val acct1 = new SavingsAccount with ConsoleLogger with
    TimestampLogger with ShortLogger
  acct1.withdraw(100) 
}

