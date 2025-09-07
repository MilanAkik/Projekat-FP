import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.{BoxPanel, Button, Component, FlowPanel, Frame, GridPanel, Label, Orientation, Swing, ToolBar}

object LevelPlayer {

  def makeToolbar(board: Board):FlowPanel = {
    val btnSave = new MenuButton("Sacuvaj"){ name = "btnSave" }
    val btnHint = new MenuButton("Pomoc") { name = "btnHint" }
    val labelScore = new MenuLabel("Rezultat: ")
    val labelTime = new MenuLabel("Vreme: ")
    new FlowPanel() {
      val elements: List[Component] = List(btnSave, btnHint, labelScore, labelTime)
      for (element <- elements){
        contents += element
        listenTo(element.mouse.clicks)
      }
      reactions += {
        case click: MouseClicked => click.source.name match
          case "btnSave" => println("Saving")
          case "btnHint" =>
            val h = board.level.Height()
            val w = board.level.Width()
            val (hintY, hintX) = board.getRandomSafeUnopened()
            val moveList = List(Move('L', hintX, hintY))
            MoveApplicator.ApplyMoves(board, moveList)
            for (j <- 0 until h; i <- 0 until w) {
              LevelPlayer.elements(j * w + i).text = mapFieldState(board.matrix(j)(i))
              LevelPlayer.elements(j * h + i).repaint()
            }
      }
    }
  }

  def mapFieldState(state:FieldState) : String = state match
    case FieldState.Unopened => "|"
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

  def makeGridButton(x: Int, y:Int, state: FieldState): Button = {
    new MenuButton(mapFieldState(state)) { name = x + "_" + y }
  }

  def makeGrid(board: Board):GridPanel = {
    val w: Int = board.level.Width()
    val h: Int = board.level.Height()
    def buttonClick(click: MouseClicked):Unit = {
      val name = click.source.name.split('_')
      val x = name(0).toInt
      val y = name(1).toInt
      val move = click.peer.getButton match {
        case java.awt.event.MouseEvent.BUTTON1 => Move('L',x,y)
        case java.awt.event.MouseEvent.BUTTON3 => Move('R',x,y)
      }
      println("Move" + move)
      MoveApplicator.ApplyMoves(board, List(move))
      for (j <- 0 until h; i <- 0 until w){
        elements(j*w+i).text = mapFieldState(board.matrix(j)(i))
        elements(j*h+i).repaint()
      }
    }
    new GridPanel(h,w){
      elements = List()
      for (j <- 0 until h; i <- 0 until w) elements = elements :+ makeGridButton(i,j,board.matrix(j)(i))
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
      val level:Level = new Level(difficulty, fileName)
      val board:Board = new Board(level)
      val elements: List[Component] = List( makeToolbar(board), Swing.VStrut(10), makeGrid(board) )
      title = "Igrajte " + difficulty
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
    }
  }

  var frame:Frame = new Frame()
  var elements: List[Button] = List()

}