package rs.ac.bg.etf.student.am233317.minesweeper

import rs.ac.bg.etf.student.am233317.minesweeper.model.Level

class LevelTests extends BaseSpec {
  "A Level" should "Be 9x9 with 10 bombs if created as easy random" in {
    val level: Level = new Level("easy", "random")
    level.Width() should be(9)
    level.Height() should be(9)
    level.matrix.flatten.count(x => x) should be(10)
  }

  it should "Be 16x16 with 15 bombs if created as medium random" in {
    val level: Level = new Level("medium", "random")
    level.Width() should be(16)
    level.Height() should be(16)
    level.matrix.flatten.count(x => x) should be(15)
  }

  it should "Be 30x16 with 20 bombs if created as hard random" in {
    val level: Level = new Level("hard", "random")
    level.Width() should be(30)
    level.Height() should be(16)
    level.matrix.flatten.count(x => x) should be(20)
  }

}
