import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(input))

println("Solution to B: " + solveB(input))



def solveA(input: String): Int = {
  return input.toList
              .foldLeft(List[Char]())(reducePolymerOnce).size
}


def reducePolymerOnce(polymer: List[Char], unit: Char): List[Char] = {
  return polymer.headOption match {
          case Some(head) => if(checkIfReact(head, unit)) polymer.tail
                             else unit :: polymer
          case None => List(unit)
         }
}


def checkIfReact(char1: Char, char2: Char): Boolean = {
  return char1.isUpper != char2.isUpper &&
         char1.toString.toLowerCase == char2.toString.toLowerCase
}



def solveB(input: String): Int = {
  val units = input.toLowerCase
                   .toSet

  return units.map(x => solveA(removeUnit(input.toList, x).mkString))
              .min
}


def removeUnit(polymer: List[Char], unitLowerCase: Char): List[Char] = {
  return polymer.filterNot(x => x.toString.toLowerCase() == unitLowerCase.toString)
}





def readInput(filename: String): String = {
  return Source.fromFile(filename)
               .getLines()
               .toList
               .head
}

