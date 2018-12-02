import scala.io.Source

val input = readInput("input.txt").map(_.toInt)

println("Solution to A: " + solveA(input, 0))
println("Solution to A(alt): " + solveA_alt(input))

println("Solution to B: " + solveB(input, Set(), 0))
println("Solution to B(alt): " + solveB_alt(input, Set(0), 0))



def solveA(input: List[Int], initialFreq: Int): Int = {
  input match {
    case x :: tail => x + solveA(tail, initialFreq)
    case Nil => initialFreq
  }
}

def solveA_alt(input: List[Int]): Int = {
  return input.sum
}


def solveB(input: List[Int], seenFreqs: Set[Int],
    initialFreq: Int): Int = {

  val (curFreq, curSeenFreqs) = solveB_internal(input, seenFreqs, initialFreq)
  curSeenFreqs match {
    case Some(x) => solveB(input, x, curFreq)
    case None => curFreq
  }
}

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


def solveB_alt(input: List[Int], prevSums: Set[Int],
    initialFreq: Int): Int = {
  val newSums = input.scanLeft(initialFreq)(_ + _).tail
  val firstDuplicate = newSums.find(x => prevSums.contains(x))

  firstDuplicate match {
    case Some(x) => x
    case None => {
      // Exclude first element, because else it will be there twice
      // - Once because it was calculated last call
      // - Another time because scanLeft adds it this call as well
      val combinedSums = prevSums ++ newSums
      solveB_alt(input, combinedSums, newSums.last)
    }
  }
}


def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}
