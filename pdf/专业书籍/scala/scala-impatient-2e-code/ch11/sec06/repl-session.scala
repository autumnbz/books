// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

17 - 2 - 9 // - is left associative
(17 - 2) - 9
17 - (2 - 9)

1 :: 2 :: Nil // :: is right associative
1 :: (2 :: Nil)
(1 :: 2) :: Nil // Error since :: can't be applied to 2

var a: Any = 3
a = a = 4 // = is right associative
a = (a = 4)
(a = a) = 4 // Error since = can't be applied to ()

var l = List(1, 2, 3)

4 :: l 
// :: is a member of the operand to the right since it ends in a colon
l.::(4)

l ::= 4 
// ::=  is a member of the operand to the left since it doesn't end in a colon



