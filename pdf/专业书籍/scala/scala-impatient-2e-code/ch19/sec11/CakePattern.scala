import java.io._

trait LoggerComponent {
  trait Logger { def log(msg: String) }

  val logger: Logger

  class ConsoleLogger extends Logger {
    def log(msg: String) { println(msg); }
  }

  class FileLogger(file: String) extends Logger { 
    val out = new PrintWriter(file) 
    def log(msg: String) { out.println(msg); out.flush() }
  }
}

trait AuthComponent {
  this: LoggerComponent => // Gives access to logger    

  trait Auth { 
    def login(id: String, password: String) = {
      if (check(id, password)) true
      else {
        logger.log("login failure for " + id)
        false
      }
    }
    def check(id: String, password: String): Boolean 
  }

  val auth: Auth

  class MockAuth(file: String) extends Auth { 
    val props = new java.util.Properties()
    props.load(new FileReader(file))
    def check(id: String, password: String) = props.getProperty(id) == password
  }
}

object AppComponents extends LoggerComponent with AuthComponent {
  val logger = new FileLogger("test.log")
  val auth = new MockAuth("users.txt")
}

object Main extends App {
  import AppComponents._
  if (auth.login("cay", "secret"))
    logger.log("cay is logged in")
  println("Look inside test.log")
}
