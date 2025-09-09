import java.io.File
import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.{BoxPanel, Button, Component, FileChooser, FlowPanel, Frame, GridPanel, Label, Orientation, Swing, ToolBar}
import java.util.{Timer, TimerTask}
import io.circe._, io.circe.parser._, io.circe.syntax._
import io.circe.generic.auto._
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

object LevelPlayer {

  def makeMove(text:String):Move = {
    val button = text(0) match {
      case 'L' => 'L'
      case 'D' => 'R'
      case _ => throw new Exception("Unknown button")
    }
    val coords = text.substring(2,text.length-1).split(',')
    Move(button, coords(0).toIntOption.get,coords(1).toIntOption.get)
  }

  def handleToolbarClick(click: MouseClicked, board: Board): Unit = {
    val h = board.level.Height()
    val w = board.level.Width()
    click.source.name match
      case "btnSave" =>
        val chooser = new FileChooser(new File(Constants.savesPaths))
        if (chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
          val save: Save = Save(board.matrix, board.level.matrix, time.seconds, score)
          val jsonString: String = save.asJson.noSpaces
          Files.write(chooser.selectedFile.toPath, jsonString.getBytes(StandardCharsets.UTF_8))
        }
      case "btnHint" =>
        val (hintY, hintX) = board.getRandomSafeUnopened
        val moveList = List(Move('L', hintX, hintY))
        MoveApplicator.ApplyMoves(board, moveList)
        for (j <- 0 until h; i <- 0 until w) {
          LevelPlayer.elements(j * w + i).text = board.matrix(j)(i).mapFieldState
          LevelPlayer.elements(j * h + i).repaint()
        }
        score = score - 2
        redrawLabels()
      case "btnMoves" =>
        val chooser = new FileChooser(new File(Constants.movesPaths))
        if( chooser.showOpenDialog(null) == FileChooser.Result.Approve ){
          val source = scala.io.Source.fromFile(chooser.selectedFile)
          val linesString = try source.mkString finally source.close()
          val lines = linesString.split("(\r\n|\n)")
          val moveList = lines.toList.map(t=>makeMove(t))

          MoveApplicator.ApplyMoves(board, moveList)
          for (j <- 0 until h; i <- 0 until w) {
            LevelPlayer.elements(j * w + i).text = board.matrix(j)(i).mapFieldState
            LevelPlayer.elements(j * h + i).repaint()
          }
          score = score - lines.length
          redrawLabels()
        }
  }

  def makeToolbar(board: Board):FlowPanel = {
    val btnSave = new MenuButton("Sacuvaj"){ name = "btnSave" }
    val btnHint = new MenuButton("Pomoc") { name = "btnHint" }
    val btnMoves = new MenuButton("Potezi") { name = "btnMoves" }

    val timer = new Timer()
    val task = new TimerTask {
      override def run(): Unit = {
        time.increment()
        if ((time.seconds % 10) == 0) score = score - 1
        redrawLabels()
      }
    }
    timer.schedule(task, 0, 1000)

    new FlowPanel() {
      val elements: List[Component] = List(btnSave, btnHint, btnMoves, labelScore, labelTime)
      for (element <- elements){
        contents += element
        listenTo(element.mouse.clicks)
      }
      reactions += { case click: MouseClicked => handleToolbarClick(click, board) }
    }
  }

  def makeGridButton(x: Int, y:Int, state: FieldState): Button = {
    new MenuButton(state.mapFieldState) { name = x + "_" + y }
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
        elements(j*w+i).text = board.matrix(j)(i).mapFieldState
        elements(j*h+i).repaint()
      }
      score = score - 1
      redrawLabels()
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
      time = new Time(0)
      score = 100
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

  def makeFrame(save: Save): Unit = {
    frame = new Frame() {
      time = new Time(save.time)
      score = save.score
      val level: Level = new Level(save)
      val board: Board = new Board(level)
      board.loadBoard(save)
      val elements: List[Component] = List(makeToolbar(board), Swing.VStrut(10), makeGrid(board))
      title = "Igrajte"
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
    }
  }

  def redrawLabels():Unit = {
    labelScore.text = "Rezultat: %03d".format(score)
    labelTime.text = "Vreme: %s".format(time.formatted)
    labelScore.repaint()
    labelTime.repaint()
  }

  var frame:Frame = new Frame()
  var elements: List[Button] = List()
  var time: Time = _
  var score: Int = _
  val labelScore = new MenuLabel("Rezultat: ")
  val labelTime = new MenuLabel("Vreme: ")

}