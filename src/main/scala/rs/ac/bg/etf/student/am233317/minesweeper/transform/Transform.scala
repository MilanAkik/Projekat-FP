package rs.ac.bg.etf.student.am233317.minesweeper.transform

trait Transform {
  def apply(level: Level, args: List[Int]): Either[Level,Error]
}
