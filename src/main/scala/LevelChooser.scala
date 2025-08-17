import java.io.File
import scala.swing.event.ButtonClicked
import scala.swing.{Button, Component, Frame, GridPanel, Swing}

object LevelChooser {

  def makeFrame(difficulty: String) : Unit = {
    frame = new Frame() {
      val elements: List[Component] = getButtonsForLevels(Constants.levelPaths, difficulty)
      title = "Izaberite nivo"
      contents = new GridPanel(3, 4) {
        for (element <- elements) contents += element
        border = Swing.EmptyBorder(20, 100, 40, 100)
        vGap = 20
      }
      for (element <- elements) listenTo(element)

      reactions += {
        case x: ButtonClicked =>
          LevelPlayer.makeFrame(difficulty+x.source.text)
          LevelPlayer.frame.visible = true
          LevelPlayer.frame.centerOnScreen()
      }
    }
  }

  var frame:Frame = new Frame()

  private def getButtonsForLevels(dir: String, difficulty: String): List[Component] = {
    val names:List[String] = getListOfFiles(dir)
    val filteredNames = names.filter(x=>x.startsWith(difficulty))
    val buttons:List[Button] = filteredNames.map(name => new Button(name))
    new Button("random")::buttons
  }

  private def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile).map(_.getName).toList
  }

}
