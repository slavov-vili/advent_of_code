import scala.io.Source



val input = readInput("input.txt")





def readInput(filename: String): Vector[Vector[Char]] = {
  return Source.fromFile(filename)
               .getLines
               .map(_.toVector)
               .toVector
}
