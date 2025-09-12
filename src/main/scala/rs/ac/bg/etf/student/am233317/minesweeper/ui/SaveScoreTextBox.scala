import scala.swing.{Alignment, Dimension, TextField}

class SaveScoreTextBox() extends TextField() {
  font = Constants.customFont
  horizontalAlignment = Alignment.Center
  xLayoutAlignment = 0.5
  maximumSize = new Dimension(Int.MaxValue, preferredSize.height)
}
