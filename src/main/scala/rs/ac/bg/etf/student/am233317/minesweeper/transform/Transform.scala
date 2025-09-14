package rs.ac.bg.etf.student.am233317.minesweeper.transform

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level

trait Transform {
  def apply(level: Level, args: Array[Int]): Either[Error,Level]
}
