package rs.ac.bg.etf.student.am233317.minesweeper.frames

import rs.ac.bg.etf.student.am233317.minesweeper.utility.Constants
import rs.ac.bg.etf.student.am233317.minesweeper.model.{Save, Score}
import rs.ac.bg.etf.student.am233317.minesweeper.ui.{MenuButton, MenuLabel}
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.generic.auto.*

import java.io.File
import scala.swing.{Alignment, *}
import scala.swing.event.*

object Main extends SimpleSwingApplication {

  def top: MainFrame = new MainFrame {
    val label:Label = new MenuLabel("Minolovac")
    val buttonPlay:Button = new MenuButton("Igraj")
    val buttonLoad: Button = new MenuButton("Ucitaj igru")
    val buttonEdit:Button = new MenuButton("Izmeni nivo")
    val buttonHighScore:Button = new MenuButton("Najbolji rezultati")
    val buttonQuit: Button = new MenuButton("Izadji")
    val elements: List[Component] = List(label, buttonPlay, buttonLoad, buttonEdit, buttonHighScore, buttonQuit)
    val strutted: List[Component] = elements.map(btn => List(btn, Swing.VStrut(20))).flatten(x => x)

    title = "Minolovac"
    contents = new BoxPanel(Orientation.Vertical) {
      for(element <- strutted) contents += element
      border = Swing.EmptyBorder(20, 60, 20, 60)
    }
    for(element <- elements) listenTo(element)

    reactions += {
      case ButtonClicked(`buttonPlay`) =>
        DifficultyChooser.frame.visible = true
        DifficultyChooser.frame.centerOnScreen()
      case ButtonClicked(`buttonLoad`) =>
        val chooser = new FileChooser(new File(Constants.savesPaths))
        if (chooser.showOpenDialog(null) == FileChooser.Result.Approve) {

          val source = scala.io.Source.fromFile(chooser.selectedFile)
          val linesString = try source.mkString finally source.close()
          val saveEither: Either[io.circe.Error, Save] = decode[Save](linesString)
          saveEither match {
            case Right(save) =>
              LevelPlayer.makeFrame(save)
              LevelPlayer.frame.visible = true
              LevelPlayer.frame.centerOnScreen()
            case Left(err) => println(s"Failed to load level: $err")
          }
        }
      case ButtonClicked(`buttonEdit`) => label.text = "We are still editing!"
      case ButtonClicked(`buttonHighScore`) => HighscorePreviewer.makeFrame()
      case ButtonClicked(`buttonQuit`) => this.close()
    }
    centerOnScreen()
  }
}