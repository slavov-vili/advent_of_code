import scala.io.Source

val startCoords = new Pair(0, 0)
val gridSize = 300
val input = 3613
val grid = generateGrid(startCoords, gridSize, input)
//println(drawGrid(grid))

// TODO: REMEMBER to add 1 to the coordinates and reverse X and Y when giving output
val solutionMax3x3 = solveA(startCoords, grid, startCoords, Int.MinValue)
println("Solution to A: " + solutionMax3x3)


def solveA(curCoords: Pair, grid: Vector[Vector[Int]], maxGridCoords: Pair, curMaxPowerLevel: Int): (Pair, Int) = {
  return if(curCoords.getX == grid.size-2) (maxGridCoords, curMaxPowerLevel)
          else {
            val (newMaxGridCoords, newMaxPowerLevel) = findCurMaxGridStart(curCoords, grid, maxGridCoords, curMaxPowerLevel)
            solveA(curCoords + new Pair(1, 0), grid, newMaxGridCoords, newMaxPowerLevel)
          }
}


def findCurMaxGridStart(curCoords: Pair, grid: Vector[Vector[Int]], curMaxGridCoords: Pair,
                        curMaxPowerLevel: Int): (Pair, Int) = {
  //println("Current Coords: " + curCoords)
  //println("Current max power level Coords: " + curMaxGridCoords)
  //println("Current max power level: " + curMaxPowerLevel)
  val x = curCoords.getX
  // Calculate the power levels of all 3x3 grids in the current 3x3 row
  val gridStartToPowerLevel = Range(curCoords.getY, grid.head.size-2).map(y => {
          val curGrid = grid.slice(x, x+3).map(_.slice(y, y+3))
          //println("Current Grid to Sum: " + new Pair(x, y) + " " + curGrid + " " + calcGridPowerLevel(curGrid))
          (new Pair(x, y), calcGridPowerLevel(curGrid))
      })
  val maxGridToPowerLevelInList = gridStartToPowerLevel.maxBy(x => x._2)
  //if(maxGridToPowerLevelInList._2 > curMaxPowerLevel)
    //println("Bigger in list than max: " + maxGridToPowerLevelInList)
  return if(maxGridToPowerLevelInList._2 > curMaxPowerLevel) maxGridToPowerLevelInList
         else (curMaxGridCoords, curMaxPowerLevel)
}


def generateGrid(startCoords: Pair, gridSize: Int, serial: Int): Vector[Vector[Int]] = {
  Range(startCoords.getX, gridSize).map(x =>
      Range(startCoords.getY, gridSize).map(y =>
        PowerCell.calcPowerLevel(new Pair(x+1, y+1), serial)
      ).toVector).toVector
}


def calcGridPowerLevel(grid: Vector[Vector[Int]]): Int = {
  return grid.map(row => row.sum).sum
}


def drawGrid(grid: Vector[Vector[Int]]): String = {
  return grid.map(row => 
         row.map(x =>
           if(x < 0) x.toString else (" " + x.toString)
         ).mkString("  ")).mkString("\n\n")
}





object PowerCell {
  def calcRackID(coords: Pair): Int = coords.getY + 10

  def calcPowerLevel(coords: Pair, gridSerialNumber: Int): Int = {
    val rackID = calcRackID(coords)

    return (((((coords.getX * rackID) +
               gridSerialNumber) * rackID) % 1000) / 100) - 5
  }
}


class Pair(x: Int, y: Int) {
  def getX(): Int = this.x;
  def getY(): Int = this.y;

  def +(that: Pair): Pair = new Pair(this.x + that.getX,
                                     this.y + that.getY)

  override def toString(): String = "(" + this.x + ", " + this.y + ")"

  override def equals(obj: Any): Boolean = obj match {
    case obj: Pair => this.x == obj.getX &&
                      this.y == obj.getY
    case _ => false
  }

}
