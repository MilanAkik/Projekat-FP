package rs.ac.bg.etf.student.am233317.minesweeper

import rs.ac.bg.etf.student.am233317.minesweeper.model.{Board, Level, Move, FieldState}
import rs.ac.bg.etf.student.am233317.minesweeper.utility.MoveApplicator

class MoveApplicatorTests extends BaseSpec {
  "A MoveApplicator" should "Return a board with all bombs revealed after the bomb field is clicked" in {
    val array: Array[Array[Boolean]] = Array.fill(5, 5)(false)
    for(i <- 0 until 5) array(i)(i) = true
    val level: Level = new Level(array)
    val board: Board = new Board(level)
    MoveApplicator.ApplyMoves(board, List(Move('L',0,0)))
    board.openedBombCount should be(5)
  }

  it should "Return a board with one flag placed" in {
    val array: Array[Array[Boolean]] = Array.fill(5, 5)(false)
    for (i <- 0 until 5) array(i)(i) = true
    val level: Level = new Level(array)
    val board: Board = new Board(level)
    MoveApplicator.ApplyMoves(board, List(Move('R', 4, 0)))
    board.openedBombCount should be(0)
    board.matrix(0)(4) should be(FieldState.Flag)
    board.matrix.flatten.count(x=>x==FieldState.Flag) should be(1)
    board.matrix.flatten.count(x=>x==FieldState.Unopened) should be(24)
  }

  it should "Return a bpard with all non bomb fields either at zero or two and all bomb fields not opened" in {
    val array: Array[Array[Boolean]] = Array.fill(5, 5)(false)
    for (i <- 0 until 5) array(i)(i) = true
    val level: Level = new Level(array)
    val board: Board = new Board(level)
    val moves: List[Move] = List(Move('L', 4, 0),Move('L', 0, 4))
    MoveApplicator.ApplyMoves(board, moves)
    board.openedBombCount should be(0)
    board.unopenedSafeCount should be(4)
    board.matrix.flatten.count(x => x == FieldState.Zero) should be(6)
    board.matrix.flatten.count(x => x == FieldState.One) should be(6)
    board.matrix.flatten.count(x => x == FieldState.Two) should be(4)
    board.matrix.flatten.count(x => x == FieldState.Unopened) should be(9)
  }
}
