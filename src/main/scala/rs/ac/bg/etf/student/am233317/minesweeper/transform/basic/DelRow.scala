package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, Transform}

class DelRow (val top: Boolean) extends Transform {

  override def apply(level: Level, args: Array[Int]): Either[Error, Level] = {
    val name = if (top) "top" else "bottom"
    if (args.length > 0) Left(Error(s"del_row_$name does not accept any arguments"))
    else {
      val w = level.Width()
      val h = level.Height()
      val res: Array[Array[Boolean]] = Array.fill(h - 1, w)(false)
      val dy = if (top) 1 else 0
      for (i <- 0 until h-1; j <- 0 until w) res(i)(j) = level.matrix(i + dy)(j)
      Right(new Level(res))
    }
  }

}
