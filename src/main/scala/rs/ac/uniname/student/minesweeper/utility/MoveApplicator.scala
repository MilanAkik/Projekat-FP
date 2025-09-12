import scala.annotation.tailrec

object MoveApplicator {
  @tailrec
  def ApplyMoves(board: Board, moves:List[Move]): Boolean = {
    val Move(button,x,y) = moves.head
    var rest = moves.tail
    val oldVal = board.matrix(y)(x)
    val newVal = button match {
      case 'L' => oldVal match {
        case FieldState.Unopened =>
          if(board.level.matrix(y)(x)){
            rest = List[Move]()
            for( j <- 0 until board.level.Height(); i <- 0 until board.level.Width() ) {
              if (board.level.matrix(j)(i) && board.matrix(j)(i) == FieldState.Unopened) rest = Move('L', i, j) :: rest
            }
            FieldState.Bomb
          }
          else getSurroundingCount(board.level, x, y) match{
            case 0 =>
              val w = board.level.Width()
              val h = board.level.Height()
              val deltas = for {
                dx <- -1 to 1
                dy <- -1 to 1
                if !(dx == 0 && dy == 0)
              } yield (dx, dy)
              deltas.map { case (dx, dy) => (x + dx, y + dy) }
                .filter { case (nx, ny) => nx >= 0 && nx < w && ny >= 0 && ny < h }
                .foreach { case (nx, ny) => rest = Move('L',nx, ny) :: rest }
              FieldState.Zero
            case 1 => FieldState.One
            case 2 => FieldState.Two
            case 3 => FieldState.Three
            case 4 => FieldState.Four
            case 5 => FieldState.Five
            case 6 => FieldState.Six
            case 7 => FieldState.Seven
            case 8 => FieldState.Eight
          }
        case _ => oldVal
      }
      case 'R' => oldVal match {
        case FieldState.Unopened => FieldState.Flag
        case FieldState.Flag => FieldState.Unopened
        case _ => oldVal
      }
      case _ => throw new IllegalArgumentException("Unexpected number: " + button)
    }
    board.matrix(y)(x) = newVal
    if (rest.nonEmpty) ApplyMoves(board, rest)
    else newVal == FieldState.Bomb
  }

  private def getSurroundingCount(level: Level, x:Int, y:Int): Int = {
    val w = level.Width()
    val h = level.Height()
    val deltas = for {
      dx <- -1 to 1
      dy <- -1 to 1
      if !(dx == 0 && dy == 0)
    } yield (dx, dy)
    deltas.count { delta =>
      val (dx, dy) = delta
      val nx = x + dx
      val ny = y + dy
      nx >= 0 && nx < w && ny >= 0 && ny < h && level.matrix(ny)(nx)
    }
  }

}
