import scala.annotation.tailrec

object MoveApplicator {
  @tailrec
  def ApplyMoves(board: Board, moves:List[Move]): Board = {
    val Move(button,x,y) = moves.head
    var rest = moves.tail
    val oldVal = board.matrix(x)(y)
    val newVal = button match {
      case 'L' => oldVal match {
        case FieldState.Unopened => {
          if(board.level.matrix(x)(y)) FieldState.Bomb
          else getSurroundingCount(board.level, x, y) match{
            case 0 => FieldState.Zero
            case 1 => FieldState.One
            case 2 => FieldState.Two
            case 3 => FieldState.Three
            case 4 => FieldState.Four
            case 5 => FieldState.Five
            case 6 => FieldState.Six
            case 7 => FieldState.Seven
            case 8 => FieldState.Eight
          }
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
    board.matrix(x)(y) = newVal
    if (rest.isEmpty) board
    else ApplyMoves(board, rest)
  }

  private def getSurroundingCount(level: Level, x:Int, y:Int): Int = {
    val left = x - 1
    val right = x + 1
    val top = y - 1
    val bottom = y + 1
    val w = level.Width()
    val h = level.Height()
    var res = 0
    if(left>=0){
      if(top>=0)
        if(level.matrix(left)(top))
          res = res + 1
      if(bottom<h)
        if(level.matrix(left)(bottom))
          res = res + 1
      if(level.matrix(left)(y))
        res = res + 1
    }
    if(right<w) {
      if (top>=0)
        if (level.matrix(right)(top))
          res = res + 1
      if (bottom < h)
        if (level.matrix(right)(bottom))
          res = res + 1
      if (level.matrix(right)(y))
        res = res + 1
    }
    if(top>=0)
      if(level.matrix(x)(top))
        res = res + 1
    if(bottom < h)
      if(level.matrix(x)(bottom))
        res = res + 1
    res
  }

}
