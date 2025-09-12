import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets.UTF_8
import scala.swing.{BoxPanel, Component, Frame, GridPanel, Orientation, Swing}
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.generic.auto.*

object HighscorePreviewer {
  def makeFrame(): Unit = {
    frame = new Frame() {
      val elements: List[Component] = getScores
      val strutted: List[Component] = elements.map(btn => List(btn, Swing.VStrut(10))).flatten(x => x)
      title = "Rezultati"
      contents = new BoxPanel(Orientation.Vertical) {
        for (element <- strutted) contents += element
        border = Swing.EmptyBorder(20, 60, 20, 60)
      }
    }
    frame.visible = true
    frame.centerOnScreen()
  }

  private def getScores: List[Component] = {
    val path = Paths.get(Constants.highscorePaths)
    val jsonString = Files.readString(path, UTF_8)
    val result: Either[io.circe.Error, List[Score]] = decode[List[Score]](jsonString)
    val list = result match
      case Right(highscores) => highscores
      case Left(_) => List()
    val finalFive = list.sortWith((a,b)=>a.score>b.score).take(5)
    new MenuLabel("Rezultati") :: finalFive.map(x => new MenuLabel(x.name + " => " + x.score.toString))
  }

  private var frame: Frame = new Frame()
}
