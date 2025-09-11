import java.io.File
import scala.swing.event.{ButtonClicked, MouseClicked, WindowClosing}
import scala.swing.{BoxPanel, Button, Component, FileChooser, FlowPanel, Frame, GridPanel, Label, Orientation, Swing, ToolBar}
import java.util.{Timer, TimerTask}
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.generic.auto.*
import java.awt.Dimension
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets.UTF_8
import java.awt.event.MouseEvent.{BUTTON1, BUTTON3}

object LevelPlayer {

  private def handleToolbarClick(click: MouseClicked)(using board: Board): Unit = {
    click.source.name match {
      case "btnSave" =>
        Ticker.stop()
        val chooser = new FileChooser(new File(Constants.savesPaths))
        if (chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
          val save: Save = Save(board.matrix, board.level.matrix, time.seconds, score)
          Files.write(chooser.selectedFile.toPath, save.asJson.noSpaces.getBytes(UTF_8))
        }
        Ticker.start(onTick, 1000)
      case "btnHint" =>
        val (hintY, hintX) = board.getRandomSafeUnopened
        onMove(board, List(Move('L', hintX, hintY)))
        score = score - 2
      case "btnMoves" =>
        Ticker.stop()
        val chooser = new FileChooser(new File(Constants.movesPaths))
        if (chooser.showOpenDialog(null) == FileChooser.Result.Approve) {
          val moves: List[Move] = MovesParser.parseMoves(chooser.selectedFile)
          onMove(board, moves)
          score = score - moves.length
        }
        Ticker.start(onTick, 1000)
    }
    redrawLabels()
  }

  private def makeToolbar()(using board: Board):FlowPanel = new FlowPanel() {
    val elements: List[Component] = List(btnSave, btnHint, btnMoves, labelScore, labelTime)
    for (element <- elements){
      contents += element
      listenTo(element.mouse.clicks)
    }
    reactions += { case click: MouseClicked => handleToolbarClick(click) }
  }

  private def makeGrid()(using board: Board):GridPanel = {
    val w: Int = board.level.Width()
    val h: Int = board.level.Height()
    new GridPanel(h,w){
      grid = List()
      for (j <- 0 until h; i <- 0 until w)
        grid = grid :+ new MenuButton(board.matrix(j)(i).mapFieldState) { name = i + "_" + j }
      for (element <- grid) {
        contents += element
        listenTo(element.mouse.clicks)
      }
      reactions += { case click: MouseClicked => gridClick(click) }
    }
  }

  def makeFrame(difficulty: String, fileName: String) : Unit = {
    time = new Time(0)
    score = 100
    val level: Level = new Level(difficulty, fileName)
    frame = new Frame() {
      val board:Board = new Board(level)
      given Board = board
      val elements: List[Component] = List( makeToolbar(), Swing.VStrut(10), makeGrid() )
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
      listenTo(this)
      reactions += { case WindowClosing(_) => onClose() }
      minimumSize = Dimension(800,600)
    }
    Ticker.start(onTick, 1000)
  }

  def makeFrame(save: Save): Unit = {
    time = new Time(save.time)
    score = save.score
    val level: Level = new Level(save)
    frame = new Frame() {
      val board: Board = new Board(level)
      board.loadBoard(save)
      given Board = board
      val elements: List[Component] = List(makeToolbar(), Swing.VStrut(10), makeGrid())
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
      listenTo(this)
      reactions += { case WindowClosing(_) => onClose() }
      minimumSize = Dimension(800,600)
    }
    Ticker.start(onTick, 1000)
  }

  private def redrawLabels():Unit = {
    labelScore.text = "Rezultat: %03d".format(score)
    labelTime.text = "Vreme: %s".format(time.formatted)
    labelScore.repaint()
    labelTime.repaint()
  }

  private val onTick:()=>Unit = () => {
    time.increment()
    if ((time.seconds % 10) == 0) score = score - 1
    redrawLabels()
  }

  private val onClose:() => Unit = () => {
    Ticker.stop()
  }

  private val onMove:(Board, List[Move]) => Unit = (board: Board, moves:List[Move]) => {
    val w: Int = board.level.Width()
    val h: Int = board.level.Height()
    val failed: Boolean = MoveApplicator.ApplyMoves(board, moves)
    if (failed || board.unopenedSafeCount == 0) Ticker.stop()
    for (j <- 0 until h; i <- 0 until w) {
      grid(j * w + i).text = board.matrix(j)(i).mapFieldState
      grid(j * w + i).repaint()
    }
  }

  private def gridClick(click: MouseClicked)(using board:Board): Unit = {
    val Array(x, y, rest*) = click.source.name.split('_')
    val move = click.peer.getButton match {
      case BUTTON1 => Move('L', x.toInt, y.toInt)
      case BUTTON3 => Move('R', x.toInt, y.toInt)
    }
    onMove(board, List(move))
    score = score - 1
    redrawLabels()
  }

  var frame:Frame = new Frame()
  private var grid : List[Button] = List()
  private var time: Time = _
  private var score: Int = _
  private val labelScore = new MenuLabel("Rezultat: ")
  private val labelTime = new MenuLabel("Vreme: ")
  private val btnSave: Button = new MenuButton("Sacuvaj") { name = "btnSave" }
  private val btnHint: Button = new MenuButton("?") { name = "btnHint" }
  private val btnMoves: Button = new MenuButton("Potezi") { name = "btnMoves" }

}