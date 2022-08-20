sealed abstract class TrafficLightColor
case object Red extends TrafficLightColor
case object Yellow extends TrafficLightColor
case object Green extends TrafficLightColor

object Main extends App {
  for (color <- Array(Red, Yellow, Green)) 
    println(
      color match {
        case Red => "stop"
        case Yellow => "hurry up"
        case Green => "go"
      })
}
