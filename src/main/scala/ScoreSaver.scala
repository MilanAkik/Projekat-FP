import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Component, Frame, Orientation, Swing}

object ScoreSaver {
  def makeFrame(callback: String => Unit): Unit = {
    val label: MenuLabel = new MenuLabel("Ime")
    val textBox: SaveScoreTextBox = new SaveScoreTextBox()
    val button: MenuButton = new MenuButton("Sacuvaj")
    frame = new Frame() {
      val elements: List[Component] = List(label, textBox, button)
      val strutted: List[Component] = elements.map(btn => List(btn, Swing.VStrut(20))).flatten(x => x)
      title = "Sacuvajte rezultat"
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- strutted) contents += element
        border = Swing.EmptyBorder(20, 60, 20, 60)
      }
      for (element <- elements) listenTo(element)
      reactions += {
        case _: ButtonClicked =>
          if(textBox.text.nonEmpty){
            callback(textBox.text)
            this.close()
          }
      }
    }
    frame.visible = true
    frame.centerOnScreen()
  }

  private var frame: Frame = new Frame()

}
