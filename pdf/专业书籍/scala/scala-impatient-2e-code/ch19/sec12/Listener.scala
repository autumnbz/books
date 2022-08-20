trait Listener {
    type Event <: java.util.EventObject
}

trait ActionListener {
  type Event = java.awt.event.ActionEvent // OK, it’s a subtype
}
