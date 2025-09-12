package rs.ac.bg.etf.student.am233317.minesweeper.ui

import rs.ac.bg.etf.student.am233317.minesweeper.utility.Constants
import scala.swing.{Alignment, Dimension, TextField}

class SaveScoreTextBox() extends TextField() {
  font = Constants.customFont
  horizontalAlignment = Alignment.Center
  xLayoutAlignment = 0.5
  maximumSize = new Dimension(Int.MaxValue, preferredSize.height)
}
