import scala.io.Source

val startCoords = new Pair(0, 0)
val gridSize = 300
val input = 3613
val grid = generateGrid(startCoords, gridSize, input)
//println(drawGrid(grid))
val initialGridMetaData = new GridMetaData(startCoords, 3, Int.MinValue)

// TODO: REMEMBER to add 1 to the coordinates and reverse X and Y when giving output
println("Solution to A: " + solveA(startCoords, grid, initialGridMetaData))

println("Solution to B: " + solveB(startCoords, 1, grid, initialGridMetaData))


def solveA(curCoords: Pair, grid: Vector[Vector[Int]], curMaxGridMetaData: GridMetaData): GridMetaData = {
  val curGridSize = curMaxGridMetaData.getSize
  return if(curCoords.getX == grid.size-(curGridSize-1)) curMaxGridMetaData
          else {
            val newMaxGridMetaData = findCurMaxGrid(curCoords, grid, curMaxGridMetaData)
            solveA(curCoords + new Pair(1, 0), grid, newMaxGridMetaData)
          }
}


// TODO: iterate until power level starts getting smaller/negative
def solveB(curCoords: Pair, curGridSize: Int, grid: Vector[Vector[Int]], curMaxGridMetaData: GridMetaData): GridMetaData = {
  val curSizeMaxGridMetaData = solveA(curCoords, grid, new GridMetaData(curCoords, curGridSize, Int.MinValue))
  println("Current grid size: " + curGridSize)
  println("Current Max Grid MetaData: " + curMaxGridMetaData)
  println("Current Size Max Grid MetaData: " + curSizeMaxGridMetaData)

  return if(curSizeMaxGridMetaData.getPowerLevel < 0)
            curMaxGridMetaData
         else solveB(curCoords, curGridSize+1, grid,
                     curMaxGridMetaData.getMorePowerful(curSizeMaxGridMetaData))
}


def findCurMaxGrid(curCoords: Pair, grid: Vector[Vector[Int]], curMaxGridMetaData: GridMetaData): GridMetaData = {
  //println("Current Coords: " + curCoords)
  //println("Current max power level Coords: " + curMaxGridCoords)
  //println("Current max power level: " + curMaxPowerLevel)
  val x = curCoords.getX
  val curGridSize = curMaxGridMetaData.getSize
  // Calculate the power levels of all 3x3 grids in the current 3x3 row
  val gridStartToPowerLevel = Range(curCoords.getY, grid.head.size-(curGridSize-1)).map(y => {
          val curGrid = grid.slice(x, x+curGridSize).map(_.slice(y, y+curGridSize))
          //println("Current Grid to Sum: " + new Pair(x, y) + " " + curGrid + " " + calcGridPowerLevel(curGrid))
          (new Pair(x, y), calcGridPowerLevel(curGrid))
      })
  // the maximum power level found in the current list of 3x3 grids
  val maxGridStartToPowerLevel = gridStartToPowerLevel.maxBy(x => x._2)
  //if(maxGridToPowerLevelInList._2 > curMaxPowerLevel)
    //println("Bigger in list than max: " + maxGridToPowerLevelInList)
  return if(maxGridStartToPowerLevel._2 > curMaxGridMetaData.getPowerLevel)
            new GridMetaData(maxGridStartToPowerLevel._1, curGridSize, maxGridStartToPowerLevel._2)
         else new GridMetaData(curMaxGridMetaData.getStartCoords, curGridSize, curMaxGridMetaData.getPowerLevel)
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





class GridMetaData(startCoords: Pair, size: Int, powerLevel: Int) {
  def getStartCoords(): Pair = this.startCoords;
  def getSize(): Int = this.size;
  def getPowerLevel(): Int = this.powerLevel

  def getMorePowerful(other: GridMetaData): GridMetaData = {
    return if(this.powerLevel > other.getPowerLevel) this
           else other
  }

  override def toString(): String = "(" + this.startCoords + " : Size = " +
                                    this.size + ", PL = " + this.powerLevel + ")"
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
