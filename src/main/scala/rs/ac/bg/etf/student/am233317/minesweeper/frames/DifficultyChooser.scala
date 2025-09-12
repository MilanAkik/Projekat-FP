package rs.ac.bg.etf.student.am233317.minesweeper.frames

import rs.ac.bg.etf.student.am233317.minesweeper.ui.{MenuButton, MenuLabel}
import scala.swing.event.ButtonClicked
import scala.swing.{Alignment, BoxPanel, Button, Component, Font, Frame, GridPanel, Label, Orientation, Swing}

object DifficultyChooser {

  private val sr2en: Map[String, String] = Map(("Lako","easy"),("Srednje","medium"),("Tesko","hard"))

  val frame:Frame = new Frame(){

    val label: Label = new MenuLabel("Izaberi tezinu")
    val buttonEasy: Button = new MenuButton("Lako")
    val buttonMedium: Button = new MenuButton("Srednje")
    val buttonHard: Button = new MenuButton("Tesko")
    val elements: List[Component] = List(label, buttonEasy, buttonMedium, buttonHard)
    val strutted: List[Component] = elements.map(btn => List(btn, Swing.VStrut(20))).flatten(x => x)

    title = "Choose the difficulty"

    contents = new BoxPanel(Orientation.Vertical) {
      for (element <- strutted) contents += element
      border = Swing.EmptyBorder(20, 60, 20, 60)
    }
    for (element <- elements) listenTo(element)

    reactions += {
      case x:ButtonClicked =>
        val b: String = sr2en.get(x.source.text) match {
          case Some(v) => v
          case None => ""
        }
        LevelChooser.makeFrame(b)
        LevelChooser.frame.visible = true
        LevelChooser.frame.centerOnScreen()
    }

  }

}
