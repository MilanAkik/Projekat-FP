package rs.ac.bg.etf.student.am233317.minesweeper

import rs.ac.bg.etf.student.am233317.minesweeper.model.{ Board, Level }

class BoardTests extends BaseSpec {
  "A Board" should "Return that all fields are unopened and have no bombs" in {
    val array: Array[Array[Boolean]] = Array.fill(5,5)(false)
    val level: Level = new Level(array)
    val board: Board = new Board(level)
    board.unopenedSafeCount should be(25)
  }

  it should "Return one less if one is set as a bomb" in {
    val array: Array[Array[Boolean]] = Array.fill(5, 5)(false)
    array(2)(2)=true
    val level: Level = new Level(array)
    val board: Board = new Board(level)
    board.unopenedSafeCount should be(24)
  }

  it should "Return the only unopened field that does not have a bomb (2,2)" in {
    val array: Array[Array[Boolean]] = Array.fill(5, 5)(true)
    array(2)(2) = false
    val level: Level = new Level(array)
    val board: Board = new Board(level)
    val (x, y) = board.getRandomSafeUnopened
    x should be(2)
    y should be(2)
  }

}
