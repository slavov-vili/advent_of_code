import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(input.map(_.toList)))

println("Solution to B: " + solveB(input.map(_.toList)))



def solveA(input: List[List[Char]]): Int = {
  val charToCounts = input.map(zipWithCount)
  val charTwiceCount = charToCounts.filter(x => x.exists(_._2 == 2)).size
  val charThreeTimesCount = charToCounts.filter(x => x.exists(_._2 == 3)).size

  return charTwiceCount * charThreeTimesCount
}


def zipWithCount(aList: List[Any]): Map[Any, Int] = {
  return aList.groupBy(x => x)
              .map(x => (x._1, x._2.size))
}



def solveB(input: List[List[Char]]): String = {
  // Converted to tuples for convenience
  val idCombos = input.combinations(2).map(x => (x.head, x.tail.head))
  val correctBoxes = idCombos.find(x => getDiffIndices(x._1, x._2).size == 1)
  
  return correctBoxes match {
           case Some(x) => getCommonElements(x._1, x._2).mkString("")
           case None => "ERROR"
         }
}


// Returns a list of indices, where the characters in the two lists differ
def getDiffIndices(list1: List[Any], list2: List[Any]): List[Int] = {
  return list1.zipWithIndex
              .diff(list2.zipWithIndex)
              .map(x => x._2)
}


// Returns a list of all common elements of the lists, which also occur at the same index
def getCommonElements(list1: List[Any], list2: List[Any]): List[Any] = {
  return list1.zipWithIndex
              .intersect(list2.zipWithIndex)
              .map(x => x._1)
}



def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}
