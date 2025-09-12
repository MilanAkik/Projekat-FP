import javax.swing.border.EmptyBorder
import scala.swing.{Alignment, Button, Dimension}

class MenuButton(labelText: String) extends Button(labelText) {
  font = Constants.customFont
  horizontalAlignment = Alignment.Center
  xLayoutAlignment = 0.5
  maximumSize = new Dimension(Int.MaxValue, preferredSize.height)
}
