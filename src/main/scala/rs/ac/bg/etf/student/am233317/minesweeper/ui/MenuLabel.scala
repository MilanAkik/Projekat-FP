package rs.ac.bg.etf.student.am233317.minesweeper.ui

import rs.ac.bg.etf.student.am233317.minesweeper.utility.Constants
import javax.swing.border.EmptyBorder
import scala.swing.{Alignment, Dimension, Label}

class MenuLabel(labelText: String) extends Label(labelText) {
  font = Constants.customFont
  horizontalAlignment = Alignment.Center
  xLayoutAlignment = 0.5
  maximumSize = new Dimension(Int.MaxValue, preferredSize.height)
}