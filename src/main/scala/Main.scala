import scala.swing._
import scala.swing.event._

object Main extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello Scala Swing"
    preferredSize = new Dimension(300, 200)

    val label = new Label("Click the button")
    val button = new Button("Click me")

    contents = new BoxPanel(Orientation.Vertical) {
      contents += label
      contents += button
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    listenTo(button)

    reactions += {
      case ButtonClicked(`button`) =>
        label.text = "Button clicked!"
    }
  }
}