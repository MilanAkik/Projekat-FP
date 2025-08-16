import scala.swing.{Alignment, Label}

class MenuLabel(labelText: String) extends Label(labelText) {
  font = Constants.customFont
  horizontalAlignment = Alignment.Center
}