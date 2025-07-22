import scala.swing.{Alignment, *}
import scala.swing.event.*

object Main extends SimpleSwingApplication {
  def top: MainFrame = new MainFrame {
    val label = new Label("Minolovac")
    val buttonPlay = new Button("Igraj")
    val buttonQuit = new Button("Izadji")
    val buttons: List[Button] = List(buttonPlay, buttonQuit)

    title = "Minolovac"
    contents = new BoxPanel(Orientation.Vertical) {
      contents += label
      for(button <- buttons) contents += button
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    for(button <- buttons) listenTo(button)

    reactions += {
      case ButtonClicked(`buttonPlay`) => label.text = "Now we are playing!"
      case ButtonClicked(`buttonQuit`) => label.text = "Quit it!"
    }
  }
}