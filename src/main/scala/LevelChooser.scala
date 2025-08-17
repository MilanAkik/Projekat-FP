import java.io.File
import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Component, Dimension, Frame, GridPanel, Label, Orientation, Swing}

object LevelChooser {

  def makeFrame(difficulty: String) : Unit = {
    frame = new Frame() {
      val elements: List[Component] = getButtonsForLevels(Constants.levelPaths, difficulty)
      title = "Izaberite nivo"
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- elements){
          contents += element
        }
        border = Swing.EmptyBorder(20, 100, 20, 100)
//        vGap = 20
      }
      for (element <- elements) listenTo(element)

      reactions += {
        case x: ButtonClicked =>
          LevelPlayer.makeFrame(x.source.text)
          LevelPlayer.frame.visible = true
          LevelPlayer.frame.centerOnScreen()
      }
    }
  }

  var frame:Frame = new Frame()

  private def getButtonsForLevels(dir: String, difficulty: String): List[Component] = {
    val names:List[String] = getListOfFiles(dir)
    val filteredNames = names.filter(x=>x.startsWith(difficulty))
    val buttons:List[Component] = filteredNames.map(name => new MenuButton(name))
    val comps = new MenuLabel("Izaberite nivo") :: (new MenuButton("random") :: buttons)
    comps.map(btn => List(btn, Swing.VStrut(20))).flatten(x => x)
//    val res:List[Component] = List();
  }

  private def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile).map(_.getName).toList
  }

}
