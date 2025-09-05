object MoveApplicator {
  def ApplyMoves(board: Board, moves:List[Move]): Board = {
    val Move(button,x,y) = moves.head
    var rest = moves.tail
    val oldVal = board.matrix(x)(y)
    val newVal = button match {
      case 'L' => {
        oldVal
      }
      case 'R' => {
        val newVal = oldVal match {
          case FieldState.Unopened => FieldState.Flag
          case FieldState.Flag => FieldState.Unopened
          case _ => oldVal
        }
        newVal
      }
      case _ => throw new IllegalArgumentException("Unexpected number: " + button)
    }
    board.matrix(x)(y) = newVal
    board
  }
}
