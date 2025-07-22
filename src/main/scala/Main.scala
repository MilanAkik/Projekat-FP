import scala.swing.{Alignment, *}
import scala.swing.event.*

object Main extends SimpleSwingApplication {
  def top: MainFrame = new MainFrame {
    title = "Minesweeper"
    preferredSize = new Dimension(300, 200)
    val label = new Label("Minesweeper")
    val buttonPlay = new Button("Play")
    val buttonQuit = new Button("Quit")
    val buttons: List[Button] = List(buttonPlay, buttonQuit)

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