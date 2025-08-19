import scala.util.Random

class Board(var l:Level) {

  val level: Level = l
  val matrix: Array[Array[FieldState]] = initBoard()

  private def initBoard(): Array[Array[FieldState]] = {
    val res = new Array[Array[FieldState]](level.Height())
    val rand = new Random(1234)
    for (i <- 0 until level.Height()) {
      res(i) = new Array[FieldState](level.Width())
      for (j <- 0 until level.Width()) {
        res(i)(j) = GetRandomFieldState(rand)
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
