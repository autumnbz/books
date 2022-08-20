// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def decorate(str: String, left: String = "[", right: String = "]") =
left + str + right

println(decorate("Hello"))

println(decorate("Hello", "<<<", ">>>"))

println(decorate("Hello", ">>>["))

println(decorate(left = "<<<", str = "Hello", right = ">>>"))

println(decorate("Hello", right = "]<<<"))
