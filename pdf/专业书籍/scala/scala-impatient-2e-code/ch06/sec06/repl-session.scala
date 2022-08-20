// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

object TrafficLightColor extends Enumeration {
  val Red, Yellow, Green = Value
}

TrafficLightColor.Red
TrafficLightColor.Red.id

object TrafficLightColor extends Enumeration {
  val Red = Value(0, "Stop")
  val Yellow = Value(10) // Name "Yellow"
  val Green = Value("Go") // ID 11
}

import TrafficLightColor._

Red
Yellow
Green
Green.id

object TrafficLightColor extends Enumeration {
  type TrafficLightColor = Value
  val Red, Yellow, Green = Value
}

import TrafficLightColor._

def doWhat(color: TrafficLightColor) = {
  if (color == Red) "stop" 
  else if (color == Yellow) "hurry up" 
  else "go"
}

doWhat(Yellow)

for (c <- TrafficLightColor.values) println(c.id + ": " + c)

TrafficLightColor(0)
TrafficLightColor.withName("Red")
