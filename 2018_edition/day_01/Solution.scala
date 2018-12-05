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


def solveB(input: List[Int], seenFreqs: Set[Int], initialFreq: Int): Int = {

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
  val combinedSums = prevSums.toList ++ newSums
  // Fails when the sum is in newSums
  val firstDuplicate = newSums.find(x => combinedSums.count(_ == x) > 1)

  firstDuplicate match {
    case Some(x) => x
    case None => {
      solveB_alt(input, combinedSums.toSet, newSums.last)
    }
  }
}


def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}
