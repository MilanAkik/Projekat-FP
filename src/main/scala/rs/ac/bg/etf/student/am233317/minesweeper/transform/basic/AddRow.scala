package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, ExpandableTrasform, Transform, TransparentTransform}

class AddRow(val top: Boolean) extends Transform {
  override def apply(level: Level, args: Array[Int]): Either[Error,Level] = {
    val name = if(top) "top" else "bottom"
    if(args.length>0) Left(Error(s"add_row_$name does not accept any arguments"))
    else {
      val w = level.Width()
      val h = level.Height()
      val res: Array[Array[Boolean]] = Array.fill(h+1, w)(false)
      val dy = if(top) 1 else 0
      for (i<- 0 until h; j <- 0 until w) res(i+dy)(j) = level.matrix(i)(j)
      Right(new Level(res))
    }
  }
}