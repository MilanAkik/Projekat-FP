package rs.ac.bg.etf.student.am233317.minesweeper.ui

import rs.ac.bg.etf.student.am233317.minesweeper.utility.Constants

import scala.swing.{Alignment, Dimension, TextArea}

class TransformField() extends TextArea() {
  font = Constants.textAreaFont
  xLayoutAlignment = 0.5
  maximumSize = new Dimension(Int.MaxValue, preferredSize.height)
  preferredSize = new Dimension(500, 200)
}
