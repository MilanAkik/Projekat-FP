import scala.swing.{Alignment, *}
import scala.swing.event.*
import io.circe._, io.circe.parser._, io.circe.syntax._
import io.circe.generic.auto._

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

    case class Cell(x: Int, y: Int, isMine: Boolean)

    val c: Cell = Cell(2, 3, true)

    // object → JSON string
    val jsonString: String = c.asJson.noSpaces

    println(jsonString)
    // {"x":2,"y":3,"isMine":true}
    val decoded: Either[io.circe.Error, Cell] = decode[Cell](jsonString)

    decoded match {
      case Right(cell) => println(s"Got cell: $cell")
      case Left(err) => println(s"Failed to decode: $err")
    }

  }
}