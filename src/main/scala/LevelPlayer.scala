import scala.swing.event.{ButtonClicked, MouseClicked}
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
    def buttonClick(click: MouseClicked):Unit = {
      val mouseBtn = click.peer.getButton match {
        case java.awt.event.MouseEvent.BUTTON1 =>"Left"
        case java.awt.event.MouseEvent.BUTTON3 =>"Right"
        case _ => "Other"
      }
      println(mouseBtn + " clicked " + click.source.name)
    }
    val w: Int = board.level.Width()
    val h: Int = board.level.Height()
    new GridPanel(w,h){
      var elements: List[Component] = List()
      for (i <- 0 until w; j <- 0 until h) elements = elements :+ makeGridButton(i,j,board.matrix(j)(i))
      for (element <- elements) {
        contents += element
        listenTo(element.mouse.clicks)
      }

      reactions += {
        case click: MouseClicked => buttonClick(click)
      }
    }
  }

  def makeFrame(difficulty: String, fileName: String) : Unit = {
    frame = new Frame() {
      val level: Level = fileName match
        case "random" => difficulty match
          case "easy" => new Level(9,9,10)
          case "medium" => new Level(16,16,40)
          case "hard" =>new Level(30,16,99)
        case _ => new Level(Constants.levelPaths+"\\"+difficulty+"\\"+fileName)
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