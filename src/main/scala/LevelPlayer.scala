import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Component, FlowPanel, Frame, GridPanel, Label, Orientation, Swing, ToolBar}

object LevelPlayer {

  def makeToolbar():FlowPanel = {
    val btnSave = new MenuButton("Sacuvaj")
    val btnHint = new MenuButton("Pomoc")
    val labelScore = new MenuLabel("Rezultat: ")
    val labelTime = new MenuLabel("Vreme: ")
    new FlowPanel() {
      val elements: List[Component] = List(btnSave, btnHint, labelScore, labelTime)
      for (element <- elements) contents += element
    }
  }

  def makeGridButton(i: Int, j:Int, label: Boolean): Button = {
    new MenuButton(if(label) "X" else "") {
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
      val level = new Level(Constants.levelPaths+"\\"+difficulty)
      val elements: List[Component] = List( makeToolbar(), Swing.VStrut(10), makeGrid(level) )
      title = "Igrajte " + difficulty
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
    }
  }

  var frame:Frame = new Frame()

}