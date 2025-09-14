package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, Transform}

class AddCol (val left: Boolean) extends Transform {
  override def apply(level: Level, args: Array[Int]): Either[Error,Level] = {
    val name = if(left) "left" else "right"
    if(args.length>0) Left(Error(s"add_col_$name does not accept any arguments"))
    else {
      val w = level.Width()
      val h = level.Height()
      val res: Array[Array[Boolean]] = Array.fill(h, w+1)(false)
      val dx = if(left) 1 else 0
      for (i<- 0 until h; j <- 0 until w) res(i)(j+dx) = level.matrix(i)(j)
      Right(new Level(res))
    }
  }
}