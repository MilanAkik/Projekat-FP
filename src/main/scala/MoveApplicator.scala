object MoveApplicator {
  def ApplyMoves(board: Board, moves:List[Move]): Board = {
    val Move(button,x,y) = moves.head
    var rest = moves.tail
    button match {
      case 'L' => 1
      case 'R' => {
        val oldVal = board.matrix(x)(y)
        val newVal = oldVal match {
          case FieldState.Unopened => FieldState.Flag
          case FieldState.Flag => FieldState.Unopened
          case _ => oldVal
        }
        board.matrix(x)(y) = newVal
      }
      case _ => throw new IllegalArgumentException("Unexpected number: "+move.button)
    }
    board
  }
}
