import scala.swing.{Alignment, *}
import scala.swing.event.*

object Main extends SimpleSwingApplication {

  private val customFont = new Font("Arial", 1000, 32)

  class MenuButton(labelText: String) extends Button(labelText) {
    font = Main.customFont
    horizontalAlignment = Alignment.Center
  }

  class MenuLabel(labelText: String) extends Label(labelText) {
    font = Main.customFont
    horizontalAlignment = Alignment.Center
  }

  def top: MainFrame = new MainFrame {
    val label:Label = new MenuLabel("Minolovac")
    val buttonPlay:Button = new MenuButton("Igraj")
    val buttonEdit:Button = new MenuButton("Izmeni nivo")
    val buttonQuit:Button = new MenuButton("Izadji")
    val elements: List[Component] = List(label, buttonPlay, buttonEdit, buttonQuit)

    title = "Minolovac"
    contents = new GridPanel(4,1) {
      for(element <- elements) contents += element
      border = Swing.EmptyBorder(20, 100, 40, 100)
      vGap = 20
    }
    for(element <- elements) listenTo(element)

    reactions += {
      case ButtonClicked(`buttonPlay`) => label.text = "Now we are playing!"
      case ButtonClicked(`buttonEdit`) => label.text = "We are still editing!"
      case ButtonClicked(`buttonQuit`) => this.close()
    }
  }
}