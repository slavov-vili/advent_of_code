import scala.io.Source

val input = readInput("input.txt")

val coordsToCounts = zipWithCount(input.flatMap(_.getCoords));

println("Solution to A: " + solveA(coordsToCounts))

println("Solution to B: " + solveB(coordsToCounts, input))



def solveA(coordsToCounts: Map[Any, Int]): Int = {
  return coordsToCounts.filter(x => x._2 > 1).size
}



def solveB(coordsToCounts: Map[Any, Int], input: List[FabricClaim]): String = {
  val bestClaim = input.find(x => x.getCoords
                                   .forall(y => coordsToCounts(y) == 1))

  bestClaim match {
    case Some(x) => x.getId
    case None => "ERROR"
  }
}



def zipWithCount(aList: List[(Any, Any)]): Map[Any, Int] = {
  return aList.groupBy(x => x)
              .map(x => (x._1, x._2.size))
}





def readInput(filename: String): List[FabricClaim] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
               .map(getClaimFromLine)
}


def getClaimFromLine(line: String): FabricClaim = {
  val id = line.split("@")(0).trim
  val rest = line.split("@")(1)

  val spaces = rest.split(":")(0)
  val spaceX = spaces.split(",")(0).trim.toInt
  val spaceY = spaces.split(",")(1).trim.toInt

  val sizes = rest.split(":")(1)
  val width = sizes.split("x")(0).trim.toInt
  val height = sizes.split("x")(1).trim.toInt

  return new FabricClaim(id, spaceX, spaceY, width, height)
}





class FabricClaim(id: String, spaceX: Int, spaceY: Int,
                  width: Int, height: Int) {
  val coords = this.calcCoords();

  private def calcCoords(): List[(Int, Int)] = {
    val coordsX = Range(1, this.width+1).map(this.spaceX + _)
    val coordsY = Range(1, this.height+1).map(this.spaceY + _)

    return coordsX.flatMap(x => coordsY.map(y => (x, y)).toList).toList
  }

  def getId(): String = this.id;

  def getCoords(): List[(Any, Any)] = this.coords;
} //end class
