// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

1 + 2 * 3 // * has higher precedence than +
1 + (2 * 3) 
(1 + 2) * 3

1 + 4 | 9 // | has lower precedence than +
(1 + 4) | 9
1 + (4 | 9)

1 + 2 to 10 // to has lower precedence than +

1 -> 2 * 3 // * has higher precedence than -> 
1 + 2 -> 3 // + has the same precedence as ->
1 -> 2 + 3 // Error--can't apply + to a pair

1 to 10 toString // Postfix operators have lower precedence than infix

