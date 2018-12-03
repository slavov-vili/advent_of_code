import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(input))

println("Solution to B: " + solveB(input))



def solveA(input: List[FabricClaim]): Int = {
  return zipWithCount(input.flatMap(_.getCoords))
              .filter(x => x._2 > 1)
              .map(_._2).size
}


def zipWithCount(aList: List[(Any, Any)]): Map[Any, Int] = {
  return aList.groupBy(x => x)
              .map(x => (x._1, x._2.size))
}



def solveB(input: List[FabricClaim]): String = {
  val faithfulCoords = zipWithCount(input.flatMap(_.getCoords)).filter(_._2 == 1)

  val bestClaim = input.find(x => x.getCoords.forall(y => faithfulCoords.contains(y)))

  bestClaim match {
    case Some(x) => x.getId
    case None => "ERROR"
  }
}





def readInput(filename: String): List[FabricClaim] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
               .map(getClaimFromLine)
}


def getClaimFromLine(line: String): FabricClaim = {
  val id = line.split("@").head.trim
  val rest = line.split("@").tail.head

  val spaces = rest.split(":").head
  val spaceX = spaces.split(",").head.trim.toInt
  val spaceY = spaces.split(",").tail.head.trim.toInt

  val sizes = rest.split(":").tail.head
  val width = sizes.split("x").head.trim.toInt
  val height = sizes.split("x").tail.head.trim.toInt

  return new FabricClaim(id, spaceX, spaceY, width, height)
}





class FabricClaim(id: String, spaceX: Int, spaceY: Int,
                  width: Int, height: Int) {
  def getId(): String = this.id;

  def getCoords(): List[(Int, Int)] = {
    val coordsX = Range(1, this.width+1).map(this.spaceX + _)
    val coordsY = Range(1, this.height+1).map(this.spaceY + _)

    return coordsX.flatMap(x => coordsY.map(y => (x, y)).toList).toList
  }
} //end class
