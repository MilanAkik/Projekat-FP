import scala.swing.{Alignment, *}
import scala.swing.event.*
import io.circe._, io.circe.parser._

object Main extends SimpleSwingApplication {

  def top: MainFrame = new MainFrame {
    val label:Label = new MenuLabel("Minolovac")
    val buttonPlay:Button = new MenuButton("Igraj")
    val buttonEdit:Button = new MenuButton("Izmeni nivo")
    val buttonHighScore:Button = new MenuButton("Najbolji rezultati")
    val buttonQuit: Button = new MenuButton("Izadji")
    val elements: List[Component] = List(label, buttonPlay, buttonEdit, buttonHighScore, buttonQuit)
    val strutted: List[Component] = elements.map(btn => List(btn, Swing.VStrut(20))).flatten(x => x)

    title = "Minolovac"
    contents = new BoxPanel(Orientation.Vertical) {
      for(element <- strutted) contents += element
      border = Swing.EmptyBorder(20, 60, 20, 60)
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
    centerOnScreen()


    val rawJson: String =
      """
    {
      "foo": "bar",
      "baz": 123,
      "list of stuff": [ 4, 5, 6 ]
    }
    """
    val parseResult: Either[ParsingFailure, Json] = parse(rawJson)
    val a: Either[ParsingFailure, Json] = parseResult

  }
}