import scala.swing.event.ButtonClicked
import scala.swing.{Alignment, Button, Component, Font, Frame, GridPanel, Label, Swing}

object DifficultyChooser {

  private val sr2en: Map[String, String] = Map(("Lako","easy"),("Srednje","medium"),("Tesko","hard"))

  val frame:Frame = new Frame(){

    val label: Label = new MenuLabel("Izaberi tezinu")
    val buttonEasy: Button = new MenuButton("Lako")
    val buttonMedium: Button = new MenuButton("Srednje")
    val buttonHard: Button = new MenuButton("Tesko")
    val elements: List[Component] = List(label, buttonEasy, buttonMedium, buttonHard)

    title = "Choose the difficulty"

    contents = new GridPanel(4, 1) {
      for (element <- elements) contents += element
      border = Swing.EmptyBorder(20, 100, 40, 100)
      vGap = 20
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
