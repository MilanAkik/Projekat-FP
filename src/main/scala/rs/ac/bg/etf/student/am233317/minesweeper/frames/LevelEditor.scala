package rs.ac.bg.etf.student.am233317.minesweeper.frames

import rs.ac.bg.etf.student.am233317.minesweeper.frames.LevelPlayer.{btnHint, btnMoves, btnSave, handleToolbarClick, labelScore, labelTime}
import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{CompositeTransform, Error, Transform}
import rs.ac.bg.etf.student.am233317.minesweeper.ui.{GridButton, MenuButton, TransformField}
import rs.ac.bg.etf.student.am233317.minesweeper.utility.{CompositeParser, Constants}

import java.io.File
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.{Files, Path, Paths}
import scala.annotation.tailrec
import scala.swing.{BoxPanel, Button, Component, Dimension, FileChooser, FlowPanel, Frame, GridPanel, Orientation, Swing}
import scala.swing.event.MouseClicked
import scala.swing.event.{ButtonClicked, MouseClicked, WindowClosing}

object LevelEditor {

  private def handleToolbarClick(click: MouseClicked): Unit = {
    click.source.name match {
      case "btnLoadComposite" =>
        val chooser = new FileChooser(new File(Constants.compositesPaths))
        if (chooser.showOpenDialog(null) == FileChooser.Result.Approve) {
          val source = scala.io.Source.fromFile(chooser.selectedFile)
          val linesString = try source.mkString finally source.close()
          txtTransforms.text = linesString
        }
      case "btnApplyComposite" =>
        val composite = new CompositeTransform(txtTransforms.text)
        composite(currentLevel,Array()) match
          case Right(lvl) => currentLevel = lvl
          case Left(err) => println(err.Message)
        val gp = makeGrid()
        boxPanel.contents.clear()
        boxPanel.contents += gp
        gp.peer.revalidate()
        gp.peer.repaint()
        boxPanel.peer.revalidate()
        boxPanel.peer.repaint()
        frame.peer.revalidate()
        frame.peer.repaint()
        frame.pack()
      case "btnSaveComposite" =>
        val chooser = new FileChooser(new File(Constants.compositesPaths))
        if (chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
          val text = txtTransforms.text
          Files.write(chooser.selectedFile.toPath, text.getBytes(UTF_8))
        }
      case "btnSaveLevel" =>
        val chooser = new FileChooser(new File(Constants.levelPaths))
        if (chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
          val text = currentLevel.matrix.map(x=>x.map(b=>if(b) '#' else '-').mkString).mkString("\n")
          Files.write(chooser.selectedFile.toPath, text.getBytes(UTF_8))
        }
    }
  }

  private def makeButtons(): BoxPanel = new BoxPanel(Orientation.Vertical) {
    private val btnLoadComposite: Button = new MenuButton("Ucitaj kompozit") { name = "btnLoadComposite" }
    private val btnApplyComposite: Button = new MenuButton("Primeni kompozit") { name = "btnApplyComposite" }
    private val btnSaveComposite: Button = new MenuButton("Sacuvaj kompozit") { name = "btnSaveComposite" }
    private val btnSaveLevel: Button = new MenuButton("Sacuvaj nivo") { name = "btnSaveLevel" }
    val elements: List[Component] = List(btnLoadComposite, btnApplyComposite, btnSaveComposite, btnSaveLevel)
    for (element <- elements) {
      contents += element
      listenTo(element.mouse.clicks)
    }
    reactions += { case click: MouseClicked => handleToolbarClick(click) }
  }

  private def makeUpperPanel(): FlowPanel = new FlowPanel(){
    val elements: List[Component] = List(txtTransforms, makeButtons())
    for (element <- elements) contents += element
  }
  private def makeGrid(): GridPanel = {
    val w: Int = currentLevel.Width()
    val h: Int = currentLevel.Height()
    var grid = List[Component]()
    new GridPanel(h, w) {
      for (j <- 0 until h; i <- 0 until w)
        grid = grid :+ new GridButton(if(currentLevel.matrix(j)(i)) "@" else " ")
      for (element <- grid) { contents += element }
    }
  }

  def makeFrame(level: Level): Unit = {
    currentLevel = level
    frame = new Frame() {
      boxPanel = new BoxPanel(Orientation.Vertical){
        contents += makeGrid()
      }
      val elements: List[Component] = List( makeUpperPanel(), Swing.VStrut(10), boxPanel)
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(10, 10, 10, 10)
      }
//      minimumSize = new Dimension(1024, 768)
    }
    frame.visible = true
    frame.centerOnScreen()
  }

  private var currentLevel: Level = _
  private var boxPanel: BoxPanel = _
  private var frame: Frame = _
  private val txtTransforms: TransformField = new TransformField()
}
