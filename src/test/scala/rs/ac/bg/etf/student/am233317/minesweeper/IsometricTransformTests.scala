package rs.ac.bg.etf.student.am233317.minesweeper

class IsometricTransformTests extends BaseSpec {

  "The AddRow" should "Return a level with one empty row at the top" in {
    def rotatePointAroundAnother(x1: Int, y1: Int, rx: Int, ry: Int, dir: Int): (Int, Int) = {
      val xprim = x1 - rx
      val yprim = y1 - ry
      dir match
        case 90 => (-yprim + rx, xprim + ry)
        case -90 => (yprim + rx, -xprim + ry)
        case _ => throw new Exception("Wrong angle")
    }
    rotatePointAroundAnother(3, 4, 1, 2, 90) should be (-1,4)
    rotatePointAroundAnother(3, 4, 1, 2, -90) should be(3, 0)
    val (nx, ny):(Int,Int) = (3,4)
    val (nx1, ny1) = rotatePointAroundAnother(nx, ny, 1, 2, 90)
    val (nx2, ny2) = rotatePointAroundAnother(nx1, ny1, 1, 2, 90)
    val (nx3, ny3) = rotatePointAroundAnother(nx2, ny2, 1, 2, 90)
    val (nx4, ny4) = rotatePointAroundAnother(nx3, ny3, 1, 2, 90)
    nx4 should be(nx)
    ny4 should be(ny)
  }
}
