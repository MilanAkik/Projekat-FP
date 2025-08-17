import scala.swing.{Component, Frame, GridPanel, Swing}

object LevelPlayer {

  def makeFrame(difficulty: String) : Unit = {
    frame = new Frame() {
      val elements: List[Component] = List()
      title = "Igrajte " + difficulty
      contents = new GridPanel(3, 4) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(20, 400, 40, 400)
        vGap = 20
      }
    }
  }

  var frame:Frame = new Frame()

}