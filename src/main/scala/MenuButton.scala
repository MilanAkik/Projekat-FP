import scala.swing.{Alignment, Button}

class MenuButton(labelText: String) extends Button(labelText) {
  font = Constants.customFont
  horizontalAlignment = Alignment.Center
}
