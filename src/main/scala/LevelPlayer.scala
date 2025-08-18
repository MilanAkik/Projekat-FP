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

  def makeGridButton(i: Int, j:Int, state: FieldState): Button = {
    var label = state match
      case FieldState.Unopened => "."
      case FieldState.Zero => " "
      case FieldState.One => "1"
      case FieldState.Two => "2"
      case FieldState.Three => "3"
      case FieldState.Four => "4"
      case FieldState.Five => "5"
      case FieldState.Six => "6"
      case FieldState.Seven => "7"
      case FieldState.Eight => "8"
      case FieldState.Flag => "P"
      case FieldState.Bomb => "@"
    new MenuButton(label) { name = i + "_" + j }
  }

  def makeGrid(board: Board):GridPanel = {
    val w: Int = board.level.Width()
    val h: Int = board.level.Height()
    new GridPanel(h,w){
      var elements: List[Component] = List()
      for (i <- 0 until w; j <- 0 until h) elements = elements :+ makeGridButton(i,j,board.matrix(i)(j))
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
      val board:Board = new Board(level)
      val elements: List[Component] = List( makeToolbar(), Swing.VStrut(10), makeGrid(board) )
      title = "Igrajte " + difficulty
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
    }
  }

  var frame:Frame = new Frame()

}