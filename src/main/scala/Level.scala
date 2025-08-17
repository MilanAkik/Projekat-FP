class Level(var path: String) {

  val matrix: Array[Array[Boolean]] = readInMatrix(path)
  def Width(): Int = matrix.length
  def Height(): Int = matrix(0).length

  private def readInMatrix(path: String):Array[Array[Boolean]] = {
    val source = scala.io.Source.fromFile(path)
    val linesString = try source.mkString finally source.close()
    val lines = linesString.split("(\r\n|\n)")
    val rows = lines.length
    val cols = lines(0).length
    val res = new Array[Array[Boolean]](rows)
    for(i <- 0 until rows){
      res(i) = new Array[Boolean](cols)
      for(j <- 0 until cols){
        res(i)(j) = lines(i)(j)=='#'
      }
    }
    res
  }

}
