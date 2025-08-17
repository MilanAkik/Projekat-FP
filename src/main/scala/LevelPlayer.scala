import scala.swing.{Button, Component, Frame, GridPanel, Label, Orientation, Swing, ToolBar}

object LevelPlayer {

  val btnSave = new Button("Sacuvaj")
  val btnHint = new Button("Pomoc")
  val labelScore = new Label("Rezultat: ")
  val labelTime = new Label("Vreme: ")

  def makeToolbar():GridPanel = {
    new GridPanel(1, 4) {
      val elements: List[Component] = List(btnSave, btnHint, labelScore, labelTime)
      for (element <- elements) contents += element
    }
  }

  def makeGrid(w: Int, h: Int):GridPanel = {
    new GridPanel(h,w){
      val elements: List[Component] = List()
      for (i <- 1 to w; j <- 1 to h) contents += new Button("("+i+","+j+")")
    }
  }

  def makeFrame(difficulty: String) : Unit = {
    frame = new Frame() {
      val elements: List[Component] = List(makeToolbar(),makeGrid(5,10))
      title = "Igrajte " + difficulty
      contents = new GridPanel(2, 1) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(20, 100, 40, 100)
        vGap = 20
      }
    }
  }

  var frame:Frame = new Frame()

}