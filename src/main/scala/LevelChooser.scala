import java.io.File
import scala.swing.{Component, Frame, GridPanel, Swing, Button}

object LevelChooser {

  val frame:Frame = new Frame() {
    val elements: List[Component] = getButtonsForLevels(Constants.levelPaths)
    title = "Izaberite nivo"
    contents = new GridPanel(3, 4) {
      for (element <- elements) contents += element
      border = Swing.EmptyBorder(20, 100, 40, 100)
      vGap = 20
    }
  }

  private def getButtonsForLevels(dir: String): List[Component] = {
    val names:List[String] = getListOfFiles(dir)
    val buttons:List[Button] = names.map(name => new Button(name))
    buttons
  }

  private def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile).map(_.getName).toList
  }

}
