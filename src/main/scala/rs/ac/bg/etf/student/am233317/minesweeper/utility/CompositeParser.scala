package rs.ac.bg.etf.student.am233317.minesweeper.utility

import rs.ac.bg.etf.student.am233317.minesweeper.transform.Transform
import rs.ac.bg.etf.student.am233317.minesweeper.transform.basic.{AddCol, AddRow, ClearArea, DelCol, DelRow, Toggle}
import rs.ac.bg.etf.student.am233317.minesweeper.utility.MovesParser.makeMove

object CompositeParser {
  def parseComposite(composite: String): List[(Transform, Array[Int])] = {
    val lines = composite.split("(\r\n|\n)")
    lines.toList.map(t => makeTransform(t))
  }

  private def makeTransform(str: String): (Transform, Array[Int]) = {
    if(str.last != ')') throw new Exception(s"Wrong transformation $str")
    val transform = str.dropRight(1)
    val components = transform.split('(')
    components(0) match
      case "add_row_top" => (new AddRow(true),Array[Int]())
      case "add_row_bottom" => (new AddRow(false),Array[Int]())
      case "add_col_left" => (new AddCol(true),Array[Int]())
      case "add_col_right" => (new AddCol(false),Array[Int]())
      case "del_row_top" => (new DelRow(true),Array[Int]())
      case "del_row_bottom" => (new DelRow(false),Array[Int]())
      case "del_col_left" => (new DelCol(true),Array[Int]())
      case "del_col_right" => (new DelCol(false),Array[Int]())
      case "toggle" =>
        val args = components(1).split(',')
        (new Toggle(), Array[Int](args(0).toInt,args(1).toInt))
      case "clear" =>
        val args = components(1).split(',')
        (new ClearArea(),Array[Int](args(0).toInt,args(1).toInt,args(2).toInt,args(3).toInt))
  }
}
