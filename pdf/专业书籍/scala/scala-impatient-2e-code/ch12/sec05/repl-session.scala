// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

(1 to 9).map(0.1 * _)
(1 to 9).map("*" * _).foreach(println _)

(1 to 9).filter(_ % 2 == 0)

(1 to 9).reduceLeft(_ * _)

"Mary had a little lamb".split(" ").sortWith(_.length < _.length)
