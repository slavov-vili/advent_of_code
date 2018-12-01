import scala.io.Source

val input = readInput("input.txt").map(_.toInt)

println("Solution to A: " + solveA(input, 0))

println("Solution to B: " + solveB(input, Set(), 0))



def solveA(input: List[Int], initialFreq: Int): Int = {
  input match {
    case x :: tail => x + solveA(tail, initialFreq)
    case Nil => initialFreq
  }
}


def solveB(input: List[Int], seenFreqs: Set[Int],
    initialFreq: Int): Int = {

  def solveB_internal(curInput: List[Int], curSeenFreqs: Set[Int],
      curFreq: Int): (Int, Option[Set[Int]]) = {
    curSeenFreqs.contains(curFreq) match {
      case true => {
        (curFreq, None)
      }
      case false => {
        curInput match {
          case x :: tail => solveB_internal(tail, curSeenFreqs + curFreq, curFreq + x)
          case Nil => (curFreq, Some(curSeenFreqs))
        }
      }
    }
  }

  val (curFreq, curSeenFreqs) = solveB_internal(input, seenFreqs, initialFreq)
  curSeenFreqs match {
    case Some(x) => solveB(input, x, curFreq)
    case None => curFreq
  }
}


def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}
