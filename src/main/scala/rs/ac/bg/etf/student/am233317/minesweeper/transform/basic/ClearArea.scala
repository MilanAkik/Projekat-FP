package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, Transform}

class ClearArea extends Transform {
  override def apply(level: Level, args: Array[Int]): Either[Error, Level] = {
    val w = level.Width()
    val h = level.Height()
    if (args.length != 4) Left(Error(s"clear expects exactly 4 arguments but recieved ${args.length}"))
    else if (args(0) < 0 || args(0) >= w) Left(Error(s"Argument x1=${args(0)} is outside of the level"))
    else if (args(1) < 0 || args(1) >= h) Left(Error(s"Argument y1=${args(1)} is outside of the level"))
    else if (args(2) < 0 || args(2) >= w) Left(Error(s"Argument x2=${args(2)} is outside of the level"))
    else if (args(3) < 0 || args(3) >= h) Left(Error(s"Argument y2=${args(3)} is outside of the level"))
    else if (args(0) > args(2)) Left(Error(s"Argument x1 cant be bigger than x2"))
    else if (args(1) > args(3)) Left(Error(s"Argument y1 cant be bigger than y2"))
    else {
      val x1 = args(0)
      val y1 = args(1)
      val x2 = args(2)
      val y2 = args(3)
      val res: Array[Array[Boolean]] = Array.tabulate(h, w)((y, x) => (x<x1||x>x2||y<y1||y>y2) && level.matrix(y)(x))
      Right(new Level(res))
    }
  }
}
