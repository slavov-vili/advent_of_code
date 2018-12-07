import scala.io.Source

val input = readInput("input.txt")

println("Solution to A: " + solveA(parseInput(input)))

println("Solution to B: " + solveB(parseInput(input)))



def solveA(input: Set[Step]): String = {
  return findStepOrder(List(), input).mkString
}


def findStepOrder(finishedStepIds: List[Char], stepSet: Set[Step]): List[Char] = {
  val nextSteps = findFreeSteps(finishedStepIds, stepSet).map(_.getId)

  return nextSteps.size match {
    case 0 => finishedStepIds.reverse
    case _ => {
      val nextStep = nextSteps.min
      findStepOrder(nextStep :: finishedStepIds, stepSet)
    }
  }
}


def findFreeSteps(finishedStepIds: List[Char], stepSet: Set[Step]): Set[Step] = {
  return stepSet.filterNot(x => finishedStepIds.contains(x.getId))
                .filter(x => x.canBegin(finishedStepIds.toSet))
}


def solveB(input: Set[Step]): Int = {
  return findSecondsNeeded(input, List(), 5, Set(), 0)
  //return -1
}


def findSecondsNeeded(stepSet: Set[Step], workers: List[Worker], maxWorkers: Int,
                      prevFinishedStepIds: Set[Char], curSecond: Int): Int = {
  //println("Current Second: " + curSecond)
  val finishedWorkers = workers.filter(x => x.hasTask)
                               .filter(x => x.isDone(curSecond, 60))
  //println("Finished workers: " + finishedWorkers)
  val busyWorkers = workers.filter(x => x.hasTask)
                           .filterNot(x => x.isDone(curSecond, 60))
  //println("Still Busy workers: " + busyWorkers)

  val newFinishedStepIds = prevFinishedStepIds.toList ++
                           finishedWorkers.map(_.getStep.getId)
  //println("New finished steps: " + newFinishedStepIds)
  val availableSteps = findFreeSteps(newFinishedStepIds, stepSet)
                        .filterNot(x => busyWorkers.exists(y => y.getStep == x))

  return if((availableSteps.size == 0) && (busyWorkers.size == 0)) curSecond
         else {
           val fillerWorkers = availableSteps.toList
                                             .sortWith((x,y) => x.getId < y.getId)
                                             .take(maxWorkers - busyWorkers.size)
                                             .map(x => new Worker(x, curSecond))
           //println("New workers: " + fillerWorkers)
           findSecondsNeeded(stepSet, busyWorkers ++ fillerWorkers, maxWorkers, newFinishedStepIds.toSet, curSecond+1)
         }
}





def readInput(filename: String): List[String] = {
  return Source.fromFile(filename)
               .getLines()
               .toList
}


def parseInput(lines: List[String]): Set[Step] = {
  val stepPattern = "(?<=.tep )([A-Z])".r
  // extract the two steps and convert them into characters
  val beforeAfterList = lines.map(x => stepPattern.findAllIn(x)
                                                  .toList
                                                  .map(_(0)))
                             .map(x => (x.head, x.tail.head))

  val possibleSteps = (beforeAfterList.map(_._1) ++
                       beforeAfterList.map(_._2)).toSet

  return possibleSteps.map(x => new Step(x, getRequirementsOf(x, beforeAfterList)))
}


def getRequirementsOf(stepId: Char, beforeAfter: List[(Char, Char)]): Set[Char] = {
  return beforeAfter.filter(_._2 == stepId)
                    .map(_._1)
                    .toSet
}





class Step(id: Char, requirements: Set[Char]) {
  def getId(): Char = this.id;
  def getReqs(): Set[Char] = this.requirements;

  def calcSeconds(extra: Int): Int = this.id - 64 + extra;

  def isOver(start: Int, curTime: Int, extraTime: Int): Boolean = {
    return curTime - start == this.calcSeconds(extraTime)
  }

  def canBegin(finishedStepIds: Set[Char]): Boolean = {
    return this.requirements.forall(x => finishedStepIds.contains(x))
  }

  override def toString(): String = {
    return "(" + this.id + " : "+ this.requirements.toString + ")"
  }

  override def equals(obj: Any): Boolean = obj match {
    case obj: Step => this.id == obj.getId &&
                      this.requirements == obj.getReqs
    case _ => false
  }

  override def hashCode() = this.id.hashCode + this.requirements.hashCode
}


class Worker(curStep: Step, workStart: Int) {
  def getStep(): Step = this.curStep;
  def getWorkStart(): Int = this.workStart;

  def hasTask(): Boolean = {
    return this.curStep.getId != ' '
  }

  def isDone(curSecond: Int, extraTime: Int): Boolean = {
    return this.curStep.isOver(this.workStart, curSecond, extraTime)
  }

  override def toString(): String = {
    return "(" + this.curStep + " : "+ this.workStart + ")"
  }

  override def equals(obj: Any): Boolean = obj match {
    case obj: Worker => this.curStep == obj.getStep &&
                        this.workStart == obj.getWorkStart
    case _ => false
  }

  override def hashCode() = this.curStep.hashCode + this.workStart.hashCode
}


object Worker {
  def createAvailable(): Worker = {
    return new Worker(new Step(' ', Set()), -1)
  }
}
