package rs.ac.bg.etf.student.am233317.minesweeper.model

case class Save(board: Array[Array[FieldState]], level: Array[Array[Boolean]], time: Int, score: Int)
