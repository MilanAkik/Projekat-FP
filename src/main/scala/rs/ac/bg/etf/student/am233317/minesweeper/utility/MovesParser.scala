package rs.ac.bg.etf.student.am233317.minesweeper.utility

import rs.ac.bg.etf.student.am233317.minesweeper.model.Move
import java.io.File

object MovesParser {

  def parseMoves(linesString: String): List[Move] = {
    val lines = linesString.split("(\r\n|\n)")
    lines.toList.map(t=>makeMove(t))
  }

  private def makeMove(text: String): Move = {
    val button = text(0) match {
      case 'L' => 'L'
      case 'D' => 'R'
      case _ => throw new Exception("Unknown button")
    }
    val coords = text.substring(2, text.length - 1).split(',')
    Move(button, coords(0).toIntOption.get, coords(1).toIntOption.get)
  }

}
