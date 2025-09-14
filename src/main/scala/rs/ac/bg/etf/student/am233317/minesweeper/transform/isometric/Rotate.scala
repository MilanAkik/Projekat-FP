package rs.ac.bg.etf.student.am233317.minesweeper.transform.isometric

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.transform.Error
import rs.ac.bg.etf.student.am233317.minesweeper.transform.IsometricTransform

class Rotate(val expandable: Boolean, val transparent: Boolean) extends IsometricTransform{

  override def apply(level: Level, args: Array[Int]): Either[Error, Level] = {
    val w = level.Width()
    val h = level.Height()
    if (args.length != 7) Left(Error(s"clear expects exactly 7 arguments but recieved ${args.length}"))
    else if (args(0) < 0 || args(0) >= w) Left(Error(s"Argument x1=${args(0)} is outside of the level"))
    else if (args(1) < 0 || args(1) >= h) Left(Error(s"Argument y1=${args(1)} is outside of the level"))
    else if (args(2) < 0 || args(2) >= w) Left(Error(s"Argument x2=${args(2)} is outside of the level"))
    else if (args(3) < 0 || args(3) >= h) Left(Error(s"Argument y2=${args(3)} is outside of the level"))
    else if (args(4) < 0 || args(4) >= h) Left(Error(s"Argument rx=${args(4)} is outside of the level"))
    else if (args(5) < 0 || args(5) >= h) Left(Error(s"Argument ry=${args(5)} is outside of the level"))
    else if (args(6) != 90 && args(6) != -90) Left(Error(s"Argument angle=${args(6)} has to be either 90 or -90"))
    else if (args(0) > args(2)) Left(Error(s"Argument x1 cant be bigger than x2"))
    else if (args(1) > args(3)) Left(Error(s"Argument y1 cant be bigger than y2"))
    else {
      val x1 = args(0)
      val y1 = args(1)
      val x2 = args(2)
      val y2 = args(3)
      val rx = args(4)
      val ry = args(5)
      val angle = args(6)
      val (nx1, ny1) = rotatePointAroundAnother(x1,y1,rx,ry,angle)
      val (nx2, ny2) = rotatePointAroundAnother(x2,y2,rx,ry,angle)
      val res: Array[Array[Boolean]] = Array.tabulate(h, w)((y, x) => level.matrix(y)(x))
      Right(new Level(res))
    }
  }

  private def rotatePointAroundAnother(x1:Int, y1:Int, rx:Int, ry:Int, dir:Int): (Int, Int) = {
    val xprim = x1-rx
    val yprim = y1-ry
    dir match
      case 90 => (-yprim+rx,xprim+ry)
      case -90 => (yprim+rx, -xprim+ry)
      case _ => throw new Exception("Wrong angle")
  }
}
