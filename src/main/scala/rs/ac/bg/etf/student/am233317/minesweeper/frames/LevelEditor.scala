package rs.ac.bg.etf.student.am233317.minesweeper.frames

import rs.ac.bg.etf.student.am233317.minesweeper.frames.LevelPlayer.{btnHint, btnMoves, btnSave, handleToolbarClick, labelScore, labelTime}
import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.ui.{ MenuButton, TransformField, GridButton }
import scala.swing.{Button, Component, FlowPanel, GridPanel, Frame, Swing, BoxPanel, Orientation, Dimension}
import scala.swing.event.MouseClicked
import scala.swing.event.{ButtonClicked, MouseClicked, WindowClosing}

object LevelEditor {

  private def handleToolbarClick(click: MouseClicked)(using level: Level): Unit = {
    click.source.name match {
      case "btnLoadComposite" => println("btnLoadComposite")
      case "btnSaveComposite" => println("btnSaveComposite")
      case "btnSaveLevel" => println("btnSaveLevel")
    }
  }

  private def makeButtons()(using level: Level): BoxPanel = new BoxPanel(Orientation.Vertical) {
    val elements: List[Component] = List(btnLoadComposite, btnSaveComposite, btnSaveLevel)
    for (element <- elements) {
      contents += element
      listenTo(element.mouse.clicks)
    }
    reactions += { case click: MouseClicked => handleToolbarClick(click) }
  }

  private def makeUpperPanel()(using level: Level): FlowPanel = new FlowPanel(){
      val elements: List[Component] = List(txtTransforms, makeButtons())
    for (element <- elements) {
      contents += element
    }
  }
  private def makeGrid()(using level: Level): GridPanel = {
    val w: Int = level.Width()
    val h: Int = level.Height()
    grid = List()
    new GridPanel(h, w) {
      for (j <- 0 until h; i <- 0 until w)
        grid = grid :+ new GridButton(if(level.matrix(j)(i)) "@" else " ")
      for (element <- grid) { contents += element }
    }
  }

  def makeFrame(level: Level): Unit = {
    frame = new Frame() {
      given Level = level
      val elements: List[Component] = List( makeUpperPanel(), Swing.VStrut(10), makeGrid())
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
//      minimumSize = Dimension(1024, 768)
    }
    frame.visible = true
    frame.centerOnScreen()
  }

  private var grid: List[Button] = List()
  private var frame: Frame = _
  private val txtTransforms: TransformField = new TransformField()
  private val btnLoadComposite: Button = new MenuButton("Ucitaj kompozit") { name = "btnLoadComposite" }
  private val btnSaveComposite: Button = new MenuButton("Sacuvaj kompozit") { name = "btnSaveComposite" }
  private val btnSaveLevel: Button = new MenuButton("Sacuvaj nivo") { name = "btnSaveLevel" }
}
