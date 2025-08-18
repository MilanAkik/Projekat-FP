class Board(var l:Level) {

  val level: Level = l
  val matrix: Array[Array[FieldState]] = initBoard()

  private def initBoard(): Array[Array[FieldState]] = {
    val res = new Array[Array[FieldState]](level.Height())
    for (i <- 0 until level.Height()) {
      res(i) = new Array[FieldState](level.Width())
      for (j <- 0 until level.Width()) {
        res(i)(j) = FieldState.Unopened
      }
    }
    res
  }

}
