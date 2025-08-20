import scala.util.Random

class Level {

  var matrix: Array[Array[Boolean]] = new Array[Array[Boolean]](1)

  val random: Random = new Random(1234)
  def Width(): Int = matrix.length
  def Height(): Int = matrix(0).length

  def this(path: String)={
    this()
    matrix = readInMatrix(path)
  }

  def this(width:Int, height:Int, mines: Int) = {
    this()
    matrix = randomLevel(width, height, mines)
  }


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

  private def randomLevel(width:Int, height:Int, mines: Int): Array[Array[Boolean]] = {
    val res = new Array[Array[Boolean]](height)
    for (i <- 0 until height) {
      res(i) = new Array[Boolean](width)
      for (j <- 0 until width) {
        res(i)(j) = false
      }
    }
    for (i <- 0 until mines){
      var col: Int = random.nextInt(width)
      var row: Int = random.nextInt(height)
      while
        res(row)(col)
      do
        col = random.nextInt(width)
        row = random.nextInt(height)
      res(row)(col)=true
    }
    res
  }

}
