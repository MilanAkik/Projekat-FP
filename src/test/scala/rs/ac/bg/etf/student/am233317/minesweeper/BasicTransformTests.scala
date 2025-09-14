package rs.ac.bg.etf.student.am233317.minesweeper

import org.scalatest.Inspectors.forAll
import org.scalatest.prop.TableDrivenPropertyChecks.whenever
import rs.ac.bg.etf.student.am233317.minesweeper.model.{Level, Save, Score}
import rs.ac.bg.etf.student.am233317.minesweeper.transform.{Error, Transform}
import rs.ac.bg.etf.student.am233317.minesweeper.transform.basic.{AddCol, AddRow}

class BasicTransformTests extends BaseSpec {

  "The AddRow" should "Return a level with one empty row at the top" in {
    val w = 5
    val h = 5
    val array: Array[Array[Boolean]] = Array.fill(h, w)(true)
    val level: Level = new Level(array)
    val t: Transform = new AddRow(true)
    val args0 = new Array[Int](0)
    val result = t(level,args0)
    val res = result.value
    res.Width() should be(5)
    res.Height() should be (6)
    forAll(res.matrix.zipWithIndex) { case (row, y) =>
      forAll(row.zipWithIndex) { case (value, _) =>
        value should be (y > 0)
      }
    }
  }

  it should "Return a level with one empty row at the bottom" in {
    val w = 5
    val h = 5
    val array: Array[Array[Boolean]] = Array.fill(h, w)(true)
    val level: Level = new Level(array)
    val t: Transform = new AddRow(false)
    val args0 = new Array[Int](0)
    val result = t(level, args0)
    val res = result.value
    res.Width() should be(5)
    res.Height() should be(6)
    forAll(res.matrix.zipWithIndex) { case (row, y) =>
      forAll(row.zipWithIndex) { case (value, _) =>
        value should be(y < h)
      }
    }
  }

  it should "Return an error when any parameters are passed" in {
    val w = 5
    val h = 5
    val array: Array[Array[Boolean]] = Array.fill(h, w)(true)
    val level: Level = new Level(array)
    val t1: Transform = new AddRow(true)
    val t2: Transform = new AddRow(false)
    val args1 = new Array[Int](1)
    val result1 = t1(level, args1)
    val result2 = t2(level, args1)
    val res1 = result1.left.value
    val res2 = result2.left.value
    res1.Message should be("add_row_top does not accept any arguments")
    res2.Message should be("add_row_bottom does not accept any arguments")
  }

  "The AddCol" should "Return a level with one empty column to the left" in {
    val w = 5
    val h = 5
    val array: Array[Array[Boolean]] = Array.fill(h, w)(true)
    val level: Level = new Level(array)
    val t: Transform = new AddCol(true)
    val args0 = new Array[Int](0)
    val result = t(level, args0)
    val res = result.value
    res.Width() should be(6)
    res.Height() should be(5)
    forAll(res.matrix.zipWithIndex) { case (row, _) =>
      forAll(row.zipWithIndex) { case (value, x) =>
        value should be(x > 0)
      }
    }
  }

  it should "Return a level with one empty column to the right" in {
    val w = 5
    val h = 5
    val array: Array[Array[Boolean]] = Array.fill(h, w)(true)
    val level: Level = new Level(array)
    val t: Transform = new AddCol(false)
    val args0 = new Array[Int](0)
    val result = t(level, args0)
    val res = result.value
    res.Width() should be(6)
    res.Height() should be(5)
    forAll(res.matrix.zipWithIndex) { case (row, _) =>
      forAll(row.zipWithIndex) { case (value, x) =>
        value should be(x < w)
      }
    }
  }

  it should "Return an error when any parameters are passed" in {
    val w = 5
    val h = 5
    val array: Array[Array[Boolean]] = Array.fill(h, w)(true)
    val level: Level = new Level(array)
    val t1: Transform = new AddCol(true)
    val t2: Transform = new AddCol(false)
    val args1 = new Array[Int](1)
    val result1 = t1(level, args1)
    val result2 = t2(level, args1)
    val res1 = result1.left.value
    val res2 = result2.left.value
    res1.Message should be("add_col_left does not accept any arguments")
    res2.Message should be("add_col_right does not accept any arguments")
  }

  /*
  val a = new Array[Array[Boolean]](2)
  a(0) = Array[Boolean](true,true)
  a(1) = Array[Boolean](true,true)
  val l: Level = new Level(a)
  val t1: Transform = new AddRow(true)
  val t2: Transform = new AddRow(false)
  val t3: Transform = new AddCol(true)
  val t4: Transform = new AddCol(false)
  val args1 = new Array[Int](0)
  val args2 = new Array[Int](1)
  printResults(t1(l, args1))
  printResults(t1(l, args2))
  printResults(t2(l, args1))
  printResults(t2(l, args2))
  printResults(t3(l, args1))
  printResults(t3(l, args2))
  printResults(t4(l, args1))
  printResults(t4(l, args2))

  def printResults(res: Either[Level, Error]): Unit = {
    res match
      case Left(level) => printLevel(level)
      case Right(error) => println(s"Failure> ${error.Message}")
  }

  def printLevel(l: Level): Unit = {
    for(i <- 0 until l.Height()){
      for(j <- 0 until l.Width()){
        print("%s\t" format l.matrix(i)(j))
      }
      print('\n')
    }
  }
  */

}
