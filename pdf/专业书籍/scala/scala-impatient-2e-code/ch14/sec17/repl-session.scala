// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val f: PartialFunction[Char, Int] = { case '+' => 1 ; case '-' => -1 }
f('-') 
f.isDefinedAt('0')
println("Throws MatchError")
f('0') 

"-3+4".collect { case '+' => 1 ; case '-' => -1 }

val names = Array("Alice", "Bob", "Carmen")
val scores = Map("Alice" -> 10, "Carmen" -> 7)
names.collect(scores) // Yields Array(10, 7)

val f: PartialFunction[Char, Int] = { case '+' => 1 ; case '-' => -1 }
val g = f.lift // A function with type Char => Option[Int]
g('-')
g('*')

val varPattern = """\{([0-9]+)\}""".r
val message = "At {1}, there was {2} on {0}"
val vars = Map("{0}" -> "planet 7", "{1}" -> "12:30 pm",
  "{2}" -> "a disturbance of the force.")
val result = varPattern.replaceSomeIn(message, m => vars.lift(m.matched))

def tryCatch[T](b: => T, catcher: PartialFunction[Throwable, T]) =
  try { b } catch catcher

val str = "Two"

val result = tryCatch(str.toInt,
  { case _: NumberFormatException => -1 })

