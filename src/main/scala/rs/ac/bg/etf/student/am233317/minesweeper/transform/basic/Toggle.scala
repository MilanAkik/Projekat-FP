package rs.ac.bg.etf.student.am233317.minesweeper.transform.basic

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{ Error, Transform }

class Toggle extends Transform {
  override def apply(level: Level, args: Array[Int]): Either[Error, Level] = {
    val w = level.Width()
    val h = level.Height()
    if (args.length != 2) Left(Error(s"toggle expects exactly 2 arguments but recieved ${args.length}"))
    else if (args(0) < 0 || args(0) >= w) Left(Error(s"Argument x=${args(0)} is outside of the level"))
    else if (args(1) < 0 || args(1) >= h) Left(Error(s"Argument y=${args(1)} is outside of the level"))
    else {
      val tx = args(0)
      val ty = args(1)
      val res: Array[Array[Boolean]] = Array.tabulate(h, w)((y, x) => level.matrix(y)(x))
      res(ty)(tx) = !res(ty)(tx)
      Right(new Level(res))
    }
  }
}
