import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Component, Frame, GridPanel, Label, Orientation, Swing, ToolBar}

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

  def makeGridButton(i: Int, j:Int, label: Boolean): Button = {
    new Button(if(label) "X" else "") {
      name = i + "_" + j
    }
  }

  def makeGrid(level: Level):GridPanel = {
    val w: Int = level.Width()
    val h: Int = level.Height()
    new GridPanel(h,w){
      var elements: List[Component] = List()
      for (i <- 0 until w; j <- 0 until h) elements = elements :+ makeGridButton(i,j,level.matrix(i)(j))
      for (element <- elements) {
        contents += element
        listenTo(element)
      }

      reactions += {
        case click: ButtonClicked => print(click.source.name)
      }
    }
  }

  def makeFrame(difficulty: String) : Unit = {
    frame = new Frame() {
      val elements: List[Component] = List(
        makeToolbar(),
        makeGrid(new Level(Constants.levelPaths+"\\"+difficulty))
      )
      title = "Igrajte " + difficulty
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(20, 20, 20, 20)
      }
    }
  }

  var frame:Frame = new Frame()

}