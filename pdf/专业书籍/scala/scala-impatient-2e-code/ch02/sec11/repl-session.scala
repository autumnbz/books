// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import javax.swing.JOptionPane._

println("Defining firstName")
val firstName = showInputDialog("Your First Name")
println("Printing once")
println(firstName)
println("Printing twice")
println(firstName)

println("Defining lastName")
lazy val lastName = showInputDialog("Your Last Name")
println("Printing once")
println(lastName)
println("Printing twice")
println(lastName)

println("Defining age")
def age = showInputDialog("Your Age")
println("Printing once")
println(age)
println("Printing twice")
println(age)








