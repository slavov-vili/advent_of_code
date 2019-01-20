import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(parseInput(input)))

println("Solution to B: " + solveB(parseInput(input)))



def solveA(coords: List[Coord]): Int = {
  val infiniteCoords = findInfiniteCoords(coords)
  val finiteCoords = coords.filterNot(x => infiniteCoords.contains(x))
  return finiteCoords.map(x => (x, findArea(x, coords, 0).size))
                     .maxBy(_._2)
                     ._2 + 1
}


def findInfiniteCoords(coords: List[Coord]): Set[Coord] = {
  val outerRimCoords = findOuterRimCoords(coords)
  return outerRimCoords.map(x => x.findClosestCoord(coords))
                                  .filterNot(_ == None)
                                  .map(_.get)
                                  .toSet
}


def findArea(myCoord: Coord, coords: List[Coord], curRadius: Int): Set[Coord] = {
  val myNeighbors = myCoord.getNeighborCoords(curRadius)
                           .map(x => (x, x.findClosestCoord(coords)))
                           .filterNot(_._2 == None)
                           .filter(_._2 == Some(myCoord))
                           .map(_._1)

  return myNeighbors.size match {
    case 0 => Set[Coord]()
    case _ => myNeighbors ++ findArea(myCoord, coords, curRadius+1)
  }
}


def findOuterRimCoords(coords: List[Coord]): Set[Coord] = {
  val rows = coords.map(_.getRow)
  val cols = coords.map(_.getCol)

  val minRow = rows.min
  val maxRow = rows.max
  val minCol = cols.min
  val maxCol = cols.max

  val topEdgeCoords    = Range(minCol, maxCol+1).map(y => new Coord(minRow, y))
  val bottomEdgeCoords = Range(minCol, maxCol+1).map(y => new Coord(maxRow, y))
  val leftEdgeCoords   = Range(minRow, maxRow+1).map(x => new Coord(x, minCol))
  val rightEdgeCoords  = Range(minRow, maxRow+1).map(x => new Coord(x, maxCol))

  return (topEdgeCoords ++ bottomEdgeCoords ++
          leftEdgeCoords ++ rightEdgeCoords).toSet
}


def solveB(coords: List[Coord]): Int = {
  val avgRow = (coords.map(_.getRow).sum / coords.size).toInt
  val avgCol = (coords.map(_.getCol).sum / coords.size).toInt
  val center = new Coord(avgRow, avgCol)
  val start  = findStart(center, coords, 0)

  return findDangerArea(start, coords, 0).size + 1
}


def findStart(center: Coord, coords: List[Coord], curRadius: Int): Coord = {
  val mySafeNeighbors = center.getNeighborCoords(curRadius)
                              .filter(x => isSafeManhSum(x, coords))

  return mySafeNeighbors.size match {
    case 0 => findStart(center, coords, curRadius+1)
    case _ => mySafeNeighbors.head
  }
}


def isSafeManhSum(myCoord: Coord, coords: List[Coord]): Boolean = {
  return coords.map(x => myCoord.calcManhDist(x)).sum < 10000
}


def findDangerArea(myCoord: Coord, coords: List[Coord], curRadius: Int): Set[Coord] = {
  val myNeighbors = myCoord.getNeighborCoords(curRadius)
                           .filter(x => isSafeManhSum(x, coords))

  return myNeighbors.size match {
    case 0 => Set[Coord]()
    case _ => myNeighbors ++ findDangerArea(myCoord, coords, curRadius+1)
  }
}





def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}


def parseInput(input: List[String]): List[Coord] = {
  // Reverse the coordinates in the input, because else I get confused
  return input.map(_.split(", "))
              .map(x => new Coord(x(1).toInt, x(0).toInt))
}





class Coord(row: Int, col: Int) {
  def getRow(): Int = this.row;
  def getCol(): Int = this.col;

  def findClosestCoord(coords: List[Coord]): Option[Coord] = {
    val coordToDist = coords.map(x => (x, this.calcManhDist(x)))
    val minDist = coordToDist.minBy(_._2)._2
    val closestCoords = coordToDist.filter(_._2 == minDist)

    return if(closestCoords.size == 1) Some(closestCoords.head._1)
           else None
  }

  def calcManhDist(other: Coord): Int = {
    return Math.abs(this.row - other.getRow) +
           Math.abs(this.col - other.getCol)
  }

  override def toString(): String = {
    return "(" + this.row + ", "+ this.col + ")"
  }

  override def equals(obj: Any): Boolean = obj match {
    case obj: Coord => this.row == obj.getRow &&
                       this.col == obj.getCol
    case _ => false
  }

  override def hashCode() = this.row.hashCode + this.col.hashCode

  def getNeighborCoords(radius: Int): Set[Coord] = {
    val neighborsPerDir  = 3 + (2 * radius)

    val topNeighborRow    = this.row - radius - 1;
    val bottomNeighborRow = this.row + radius + 1;
    val leftNeighborCol   = this.col - radius - 1;
    val rightNeighborCol  = this.col + radius + 1;
    
    val topNeighborCoords =
      Range(0, neighborsPerDir).map(x => new Coord(topNeighborRow,
                                                   leftNeighborCol + x))
    val bottomNeighborCoords =
      Range(0, neighborsPerDir).map(x => new Coord(bottomNeighborRow,
                                                   leftNeighborCol + x))
    val leftNeighborCoords =
      Range(0, neighborsPerDir).map(x => new Coord(topNeighborRow + x,
                                                   leftNeighborCol))
    val rightNeighborCoords =
      Range(0, neighborsPerDir).map(x => new Coord(topNeighborRow + x,
                                                   rightNeighborCol))

    return (topNeighborCoords ++
           bottomNeighborCoords ++
           leftNeighborCoords ++
           rightNeighborCoords).toSet
  }
}
