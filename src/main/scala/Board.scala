class Board(var width: Int, var height: Int) {

  val matrix: Array[Array[FieldState]] = initBoard()

  private def initBoard(): Array[Array[FieldState]] = {
    val res = new Array[Array[FieldState]](height)
    for (i <- 0 until height) {
      res(i) = new Array[FieldState](width)
      for (j <- 0 until width) {
        res(i)(j) = FieldState.Unopened
      }
    }
    res
  }

}
