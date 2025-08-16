import scala.swing.{Alignment, Button, Component, Font, Frame, GridPanel, Label, Swing}

object DifficultyChooser {

  private val customFont = new Font("Arial", 1000, 32)

  class ChooserLabel(labelText: String) extends Label(labelText) {
    font = DifficultyChooser.customFont
    horizontalAlignment = Alignment.Center
  }

  class ChooserButton(labelText: String) extends Button(labelText) {
    font = DifficultyChooser.customFont
    horizontalAlignment = Alignment.Center
  }

  val frame:Frame = new Frame(){

    val label: Label = new ChooserLabel("Izaberi tezinu")
    val buttonEasy: Button = new ChooserButton("Lako")
    val buttonMedium: Button = new ChooserButton("Srednje")
    val buttonHard: Button = new ChooserButton("Tesko")
    val elements: List[Component] = List(label, buttonEasy, buttonMedium, buttonHard)

    title = "Choose the difficulty"

    contents = new GridPanel(4, 1) {
      for (element <- elements) contents += element
      border = Swing.EmptyBorder(20, 100, 40, 100)
      vGap = 20
    }

  }

}
