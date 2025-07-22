import scala.swing.{Alignment, *}
import scala.swing.event.*

object Main extends SimpleSwingApplication {

  val customFont = new Font("Arial", 1000, 32)

  def top: MainFrame = new MainFrame {
    val label:Label = new Label("Minolovac"){
      font = customFont
      override def horizontalAlignment: Alignment.Value = Alignment.Center
    }
    val buttonPlay:Button = new Button("Igraj"){
      font = customFont
      override def horizontalAlignment: Alignment.Value = Alignment.Center
      margin = new Insets(10,10,10,10)
    }
    val buttonEdit:Button = new Button("Izmeni nivo") {
      font = customFont
      override def horizontalAlignment: Alignment.Value = Alignment.Center
      margin = new Insets(10, 10, 10, 10)
    }
    val buttonQuit:Button = new Button("Izadji"){
      font = customFont
      override def horizontalAlignment: Alignment.Value = Alignment.Center
    }
    val buttons: List[Button] = List(buttonPlay, buttonEdit, buttonQuit)

    title = "Minolovac"
    contents = new GridPanel(4,1) {
      contents += label
      for(button <- buttons) contents += button
      border = Swing.EmptyBorder(10, 100, 10, 100)
      vGap = 20
    }
    for(button <- buttons) listenTo(button)

    reactions += {
      case ButtonClicked(`buttonPlay`) => label.text = "Now we are playing!"
      case ButtonClicked(`buttonEdit`) => label.text = "We are still editing!"
      case ButtonClicked(`buttonQuit`) => label.text = "Quit it!"
    }
  }
}