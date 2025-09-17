package rs.ac.bg.etf.student.am233317.minesweeper.transform
import rs.ac.bg.etf.student.am233317.minesweeper.model.Level
import rs.ac.bg.etf.student.am233317.minesweeper.utility.CompositeParser

import scala.annotation.tailrec

class CompositeTransform(text: String) extends Transform {

  val transforms: List[(Transform, Array[Int])] = CompositeParser.parseComposite(text)

  override def apply(level: Level, args: Array[Int]): Either[Error, Level] = {
    if (args.length != 0) Left(Error(s"clear expects exactly 0 arguments but recieved ${args.length}"))
    else {
      transformApply(level, transforms) match
        case Left(x) => Left(x)
        case Right(x) => Right(x)
    }
  }

  @tailrec()
  private def transformApply(l: Level, t: List[(Transform, Array[Int])]): Either[Error, Level] = {
    val head = t.head
    val tail = t.tail
    val tailEmpty = tail.isEmpty
    val (tr, args) = head
    val res = tr(l, args)
    (tailEmpty, res) match
      case (true, Right(lvl)) => Right(lvl)
      case (false, Right(lvl)) => transformApply(lvl, tail)
      case (_, Left(err)) => Left(err)
  }

}
