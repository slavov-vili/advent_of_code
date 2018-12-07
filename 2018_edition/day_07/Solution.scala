import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(parseInput(input)))

// println("Solution to B: " + solveB(input))


// need: finished steps, all steps, remaining rules


// FIXME: yeah
def solveA(input: List[(Char, Char)]): String = {
  val requirements = input.map(_._1)
  val lockedSteps  = input.map(_._2).toSet
  val initialSteps  = requirements.filterNot(x => lockedSteps.contains(x))
                                  .toSet
                                  .toList
  println("Initial: " + initialSteps)
  return findStepOrder(initialSteps, input.toSet).mkString
}


def findStepOrder(finishedSteps: List[Char], stepRules: Set[(Char, Char)]): List[Char] = {
  val availableRules = stepRules.filter(x => finishedSteps.contains(x._1))
                                .filterNot(x => finishedSteps.contains(x._2))

  return availableRules.size match {
    case 0 => finishedSteps.reverse
    case _ => {
      val curStepRule = availableRules.minBy(_._2)
      findStepOrder(curStepRule._2 :: finishedSteps, stepRules - curStepRule)
    }
  }
}





def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}


def parseInput(lines: List[String]): List[(Char, Char)] = {
  val stepPattern = "(?<=.tep )([A-Z])".r
  val beforeAfterList = lines.map(x => stepPattern.findAllIn(x).toList)

  // extract the two steps and convert them into characters
  return beforeAfterList.map(x => (x.head(0), x.tail.head(0)))
}

