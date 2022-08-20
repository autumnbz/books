// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val numPattern = "[0-9]+".r

val wsnumwsPattern = """\s+[0-9]+\s+""".r

for (matchString <- numPattern.findAllIn("99 bottles, 98 bottles"))
  println(matchString)

val matches = wsnumwsPattern.findFirstIn("99 bottles, 98 bottles")

val firstMatch = wsnumwsPattern.findFirstIn("99 bottles, 98 bottles")


val anchoredPattern = "^[0-9]+$".r
val str = " 123"
if (anchoredPattern.findFirstIn(str) == None) println("Not a number")
if (str.matches("[0-9]+")) println("A number")

numPattern.replaceFirstIn("99 bottles, 98 bottles", "XX")

numPattern.replaceAllIn("99 bottles, 98 bottles", "XX")

numPattern.replaceSomeIn("99 bottles, 98 bottles",
  m => if (m.matched.toInt % 2 == 0) Some("XX") else None)

val varPattern = """\$[0-9]+""".r
def format(message: String, vars: String*) =
  varPattern.replaceSomeIn(message, m => vars.lift(
    m.matched.tail.toInt))
format("At $1, there was $2 on $0.",
  "planet 7", "12:30 pm", "a disturbance of the force")
