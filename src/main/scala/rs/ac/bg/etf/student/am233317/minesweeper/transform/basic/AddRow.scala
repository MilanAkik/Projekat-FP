package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, ExpandableTrasform, Transform, TransparentTransform}

class AddRow(val top: Boolean) extends Transform {
  override def apply(level: Level, args: Array[Int]): Either[Level, Error] = {
    val name = if(top) "top" else "bottom"
    if(args.length>0) Right(Error(s"The add_row_$name"))
    else {
      Left(level)
    }
  }
}