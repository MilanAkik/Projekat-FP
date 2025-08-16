import scala.swing.{Alignment, *}
import scala.swing.event.*

object Main extends SimpleSwingApplication {

  def top: MainFrame = new MainFrame {
    val label:Label = new MenuLabel("Minolovac")
    val buttonPlay:Button = new MenuButton("Igraj")
    val buttonEdit:Button = new MenuButton("Izmeni nivo")
    val buttonHighScore:Button = new MenuButton("Najbolji rezultati")
    val buttonQuit: Button = new MenuButton("Izadji")
    val elements: List[Component] = List(label, buttonPlay, buttonEdit, buttonHighScore, buttonQuit)

    title = "Minolovac"
    contents = new GridPanel(5,1) {
      for(element <- elements) contents += element
      border = Swing.EmptyBorder(20, 100, 40, 100)
      vGap = 20
    }
    for(element <- elements) listenTo(element)

    reactions += {
      case ButtonClicked(`buttonPlay`) =>
        DifficultyChooser.frame.visible = true
        DifficultyChooser.frame.centerOnScreen()
      case ButtonClicked(`buttonEdit`) => label.text = "We are still editing!"
      case ButtonClicked(`buttonHighScore`) => label.text = "No high score!"
      case ButtonClicked(`buttonQuit`) => this.close()
    }
  }
}