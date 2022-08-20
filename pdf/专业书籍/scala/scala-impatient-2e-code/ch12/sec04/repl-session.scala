// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

valueAtOneQuarter((x: Double) => 3 * x)
valueAtOneQuarter((x) => 3 * x)
valueAtOneQuarter(x => 3 * x)
valueAtOneQuarter(3 * _)

val fun = 3 * _ // Error: Can’t infer types
val fun = 3 * (_: Double) // OK
val fun: (Double) => Double = 3 * _ // OK 

