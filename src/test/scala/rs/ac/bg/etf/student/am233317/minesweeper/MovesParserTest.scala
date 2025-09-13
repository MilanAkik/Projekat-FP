package rs.ac.bg.etf.student.am233317.minesweeper

import rs.ac.bg.etf.student.am233317.minesweeper.model.Move
import rs.ac.bg.etf.student.am233317.minesweeper.utility.MovesParser

class MovesParserTest extends BaseSpec {
  "A MovesParser" should "Read one left click move on (0,0)" in {
    val moveString = "L(0,0)"
    val moves = MovesParser.parseMoves(moveString)
    moves.length should be(1)
    moves.head should be(Move('L',0,0))
  }

  it should "Read two left clicks on (0,0)" in {
    val moveString = "L(0,0)\nL(0,0)"
    val moves = MovesParser.parseMoves(moveString)
    moves.length should be(2)
    moves.head should be(Move('L', 0, 0))
    moves(1) should be(Move('L', 0, 0))
  }

  it should "Read a sequence of 6 clicks - even click left on (0,0) - odd right on (1,1)" in {
    val moveString = "L(0,0)\nD(1,1)\nL(0,0)\nD(1,1)\nL(0,0)\nD(1,1)"
    val moves = MovesParser.parseMoves(moveString)
    moves.length should be(6)
    moves.head should be(Move('L', 0, 0))
    moves(1) should be(Move('R', 1, 1))
    moves(2) should be(Move('L', 0, 0))
    moves(3) should be(Move('R', 1, 1))
    moves(4) should be(Move('L', 0, 0))
    moves(5) should be(Move('R', 1, 1))
  }

}
