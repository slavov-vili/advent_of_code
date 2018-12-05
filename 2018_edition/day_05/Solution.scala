import scala.io.Source

val input = readInput("input.txt")

// TODO: think of alt version where it just takes tuples or make recursion more efficient
// println("Solution to A: " + solveA(input.toList))

println("Solution to B: " + solveB(input.toList))



// NOTE: it's EXTREMELY slow due to either too large input for such recursion
//       OR (more likely) -> inefficient recursion
// NOTE: produces a stackoverflow, so you need to make the stack bigger (scala -J-Xss200m)
def solveA(input: List[Char]): Int = {
  return reducePolymer(input).size
}


def reducePolymer(polymer: List[Char]): List[Char] =  {
  val newPolymer = reducePolymer_helper(polymer, ' ')
  // println("Polymer size: " + polymer.size)

  return if(polymer.size == newPolymer.size) newPolymer
         else reducePolymer(newPolymer)
}


def reducePolymer_helper(polymer: List[Char], lastChar: Char): List[Char] = {
  val newPolymer = polymer match {
                     case Nil => List(lastChar)
                     case head :: tail => {
                       if(checkIfReact(lastChar, head)) {
                         // println("Reduced: " + lastChar + " & " + head)
                         reducePolymer_helper(tail, ' ')
                       }
                       else lastChar :: reducePolymer_helper(tail, head)
                     }
                   }
  return newPolymer.filterNot(x => x == ' ')
}


def checkIfReact(char1: Char, char2: Char): Boolean = {
  return char1.isUpper != char2.isUpper &&
         char1.toString.toLowerCase == char2.toString.toLowerCase
}



// NOTE: it's EXTREMELY slow due to either too large input for such recursion
//       OR (more likely) -> inefficient recursion
// NOTE: produces a stackoverflow, so you need to make the stack bigger (scala -J-Xss200m)
def solveB(input: List[Char]): Int = {
  val units = input.map(_.toString.toLowerCase()(0)).toSet
    
  val unitToEffect = units.map(x => (x, solveA(removeUnit(input, x))))

  unitToEffect.foreach(x => println(x._1 + " -> " + x._2))
  
  return unitToEffect.maxBy(_._2)
                     ._2
}


def removeUnit(polymer: List[Char], unitLowerCase: Char): List[Char] = {
  return polymer.filterNot(x => x.toString.toLowerCase()(0) == unitLowerCase)
}





def readInput(filename: String): String = {
  return Source.fromFile(filename)
               .getLines()
               .toList
               .head
}

