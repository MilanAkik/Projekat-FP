package rs.ac.bg.etf.student.am233317.minesweeper.ui

import rs.ac.bg.etf.student.am233317.minesweeper.utility.Constants
import scala.swing.{Alignment, Button, Dimension}

class GridButton(labelText: String) extends Button(labelText) {
  font = Constants.gridFont
  horizontalAlignment = Alignment.Center
  xLayoutAlignment = 0.5
  maximumSize = new Dimension(50, 50)
  preferredSize = new Dimension(50, 50)
  minimumSize = new Dimension(50, 50)
}
