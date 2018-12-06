import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(input))

println("Solution to B: " + solveB(input))



def solveA(coords: List[Coord]): = {
  val finiteCoords = coords.filterNot(x => findInfiniteCoords(coords).contains(x))

  val areas = finiteCoords.map(x => findArea(x, coords, Set()))

}


def findInfiniteCoords(coords: List[Coord]): List[Coord] = {
  val minCoordLeft   = coords.minBy(x => x.getCol)
  val minCoordRight  = coords.maxBy(x => x.getCol)
  val minCoordTop    = coords.minBy(x => x.getRow)
  val minCoordBottom = coords.maxBy(x => x.getRow)

  val closestLeft   =  coords.filter(x => x.getCol == minCoordLeft)
  val closestRight  =  coords.filter(x => x.getCol == minCoordRight)
  val closestTop    =  coords.filter(x => x.getRow == minCoordTop)
  val closestBottom =  coords.filter(x => x.getRow == minCoordBottom)

  return closestLeft ++ closestRight ++
         closestTop ++ closestBottom
}


// TODO: generate neighbors at dist until none are closest to myCoord
// neighborDist = exactly how far away the generated neighbors should be
def findArea(myCoord: Coord, coords: List[Coord], curArea: Set[Coord]): Set[Coord] = {
  val nextArea = aCoord.getNeighborCoords(neighborDist)

}





def readInput(filename: String): String = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}


def parseInput(input: List[String]): List[Coord] = {
  return input.map(_.split(", "))
              .map(x => new Coord(x(0).toInt, x(1).toInt))
}





class Coord(row: Int, col: Int) {
  def getRow(): Int = this.row;
  def getCol(): Int = this.col;

  def findClosestCoord(coords: List[Coord]): Option(Coord) = {
    val coordToDist coords.map(x => (x, this.calcManhDist(x)))
    val minDist = coordToDist.minBy(_._2)._2
    val closestCoords = coordToDist.filter(_._2 == minDist)

    return if(closestCoords.size == 1) Some(closestCoords.head)
           else None
  }

  def calcManhDist(other: Coord): Int = {
    return Math.abs(this.row - other.getRow) +
           Math.abs(this.col - other.getCol)
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
