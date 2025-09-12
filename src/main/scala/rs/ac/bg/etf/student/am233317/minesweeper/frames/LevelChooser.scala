package rs.ac.bg.etf.student.am233317.minesweeper.frames

import rs.ac.bg.etf.student.am233317.minesweeper.utility.Constants
import rs.ac.bg.etf.student.am233317.minesweeper.model.Score
import rs.ac.bg.etf.student.am233317.minesweeper.ui.{MenuButton, MenuLabel}
import java.io.File
import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Component, Dimension, Frame, GridPanel, Label, Orientation, Swing}

object LevelChooser {

  def makeFrame(difficulty: String) : Unit = {
    frame = new Frame() {
      val elements: List[Component] = getButtonsForLevels(Constants.levelPaths+"\\"+difficulty)
      val strutted: List[Component] = elements.map(btn => List(btn, Swing.VStrut(20))).flatten(x => x)
      title = "Izaberite nivo"
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- strutted) contents += element
        border = Swing.EmptyBorder(20, 60, 20, 60)
      }
      for (element <- elements) listenTo(element)

      reactions += {
        case x: ButtonClicked =>
          LevelPlayer.makeFrame(difficulty,x.source.text)
          LevelPlayer.frame.visible = true
          LevelPlayer.frame.centerOnScreen()
      }
    }
  }

  var frame:Frame = new Frame()

  private def getButtonsForLevels(dir: String): List[Component] = {
    val names:List[String] = getListOfFiles(dir)
    val buttons:List[Component] = names.map(name => new MenuButton(name))
    new MenuLabel("Izaberite nivo") :: (new MenuButton("random") :: buttons)
  }

  private def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile).map(_.getName).toList
  }

}
