// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

abstract class UndoableAction(val description: String) {
  def undo(): Unit
  def redo(): Unit
}

object DoNothingAction extends UndoableAction("Do nothing") {
  override def undo() {}
  override def redo() {}
}

val actions = Map("open" -> DoNothingAction, "save" -> DoNothingAction)
actions("open").description
actions("open") == actions("save")
