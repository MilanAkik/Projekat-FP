package rs.ac.bg.etf.student.am233317.minesweeper.model

import scala.util.Random

class Board(var l:Level) {

  val level: Level = l
  var matrix: Array[Array[FieldState]] = initBoard()

  def loadBoard(save: Save): Unit = {
    matrix = save.board
  }

  def unopenedSafeCount : Int = {
    val deltas = for {
      row <- 0 until level.Height()
      col <- 0 until level.Width()
      if !level.matrix(row)(col) && matrix(row)(col) == FieldState.Unopened
    } yield (row, col)
    deltas.count(x=>true)
  }

  def getRandomSafeUnopened: (Int, Int) = {
    val rand = new Random(1234)
    val deltas = for {
      row <- 0 until level.Height()
      col <- 0 until level.Width()
      if !level.matrix(row)(col) && matrix(row)(col)==FieldState.Unopened
    } yield (row, col)
    deltas(rand.nextInt(deltas.length))
  }

  private def initBoard(): Array[Array[FieldState]] = {
    val res = new Array[Array[FieldState]](level.Height())
    val rand = new Random(1234)
    for (i <- 0 until level.Height()) {
      res(i) = new Array[FieldState](level.Width())
      for (j <- 0 until level.Width()) {
        res(i)(j) = FieldState.Unopened
      }
    }
    res
  }

  private def GetRandomFieldState(rand: Random): FieldState = {
    rand.nextInt(12) match
      case 0 => FieldState.Unopened
      case 1 => FieldState.Zero
      case 2 => FieldState.One
      case 3 => FieldState.Two
      case 4 => FieldState.Three
      case 5 => FieldState.Four
      case 6 => FieldState.Five
      case 7 => FieldState.Six
      case 8 => FieldState.Seven
      case 9 => FieldState.Eight
      case 10 => FieldState.Flag
      case 11 => FieldState.Bomb
  }

}
