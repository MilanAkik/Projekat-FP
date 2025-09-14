package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, Transform}

class DelCol (val left: Boolean) extends Transform {
  override def apply(level: Level, args: Array[Int]): Either[Error, Level] = {
    val name = if (left) "left" else "right"
    if (args.length > 0) Left(Error(s"del_col_$name does not accept any arguments"))
    else {
      val w = level.Width()
      val h = level.Height()
      val res: Array[Array[Boolean]] = Array.fill(h, w - 1)(false)
      val dx = if (left) 1 else 0
      for (i <- 0 until h; j <- 0 until w-1) res(i)(j) = level.matrix(i)(j + dx)
      Right(new Level(res))
    }
  }
}
