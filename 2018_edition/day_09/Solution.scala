import scala.io.Source

val input = readInput("input.txt")

val rules = parseInput(input, 23).get

val (endCircle, endResults) = playGame(rules, 0, List(new Marble(0, 0, None)), 0, Map())

println("Solution to A: " + solveA(endResults))

//println("Solution to B: " + solveB(root))

// TODO: think of processing it in batches of 23?

// TODO: make another class for the circle, which handles removals / additions?

def solveA(results: Map[Int, Set[Marble]]): Int = {
  return results.map(x => x._2.map(_.getNumber).sum)
                .max
}



// TODO: replace curMarbleNumber with idx
// TODO: remove index from marble object
def playGame(rules: Rules, curMarbleNumber: Int, gameCircle: List[Marble],
             curPlayerId: Int, playerIdToMarbles: Map[Int, Set[Marble]]): (List[Marble], Map[Int, Set[Marble]]) = {
  //println()
  //println("Playing: ")
  //println(rules)
  //println("Current Player ID: " + curPlayerId)
  //println("Circle size: " + gameCircle.size)
  //println("Circle:")
  //gameCircle.foreach(println)
  //println("Scores size: " + playerIdToMarbles.flatMap(_._2).size)
  //println("Scores:")
  //playerIdToMarbles.foreach(println)

  val totalMarbleCount = gameCircle.size + playerIdToMarbles.flatMap(_._2).size
  //println("Total Marble Count: " + totalMarbleCount)

  return if(totalMarbleCount > rules.getMarbleCount) (gameCircle, playerIdToMarbles)
         else {
           val curMarble = gameCircle.find(_.getNumber == curMarbleNumber)
                                     .get
           println("Current Marble: " + curMarble)
           val (newCurMarbleNumber, newCircle, newScores) =
             takeTurn(rules, curMarble, gameCircle, curPlayerId, playerIdToMarbles)
           playGame(rules, newCurMarbleNumber, newCircle,
                    (curPlayerId + 1) % rules.getPlayerCount, newScores)
         }
}


// TODO: since prepending, left is clockwise and right is counter-clockwise
def takeTurn(rules: Rules, lastMarble: Marble, gameCircle: List[Marble],
             curPlayerId: Int, playerIdToMarbles: Map[Int, Set[Marble]]):
            (Int, List[Marble], Map[Int, Set[Marble]]) = {
  //println(curPlayerId + "'s turn")
  val circleSize = gameCircle.size
  val lastMarbleIndex = lastMarble.getIndex
  //println("Circle Size: " + circleSize)
  //println("Collected Marbles: " + playerIdToMarbles.flatMap(_._2).size)
  //println("Current Marble Index: " + lastMarbleIndex)
  // Next marble = # current marbles in the field + # collected marbles
  val nextMarbleNumber = circleSize + playerIdToMarbles.flatMap(_._2)
                                                       .size
  return if(nextMarbleNumber % rules.getMagicNumber != 0) {
           val nextMarbleIndex = ((lastMarbleIndex + 1) % circleSize) + 1
           println("Next Marble Index : " + nextMarbleIndex)
           println("Next Marble Number : " + nextMarbleNumber)
           val nextMarble = new Marble(nextMarbleIndex, nextMarbleNumber, None)
           // TODO: replace with splitAt
           val (prevMarbles, nextMarbles) = gameCircle.partition(_.getIndex < nextMarbleIndex)
           // update the indices of all marbles after the next one
           return (nextMarbleNumber,
                   nextMarble :: prevMarbles ++ nextMarbles.map(x => x.setIndex(x.getIndex+1)),
                   playerIdToMarbles)
         } else {
           // should take care of the cases where curIndex < 7
           val sevenBehindIndex = (circleSize + lastMarbleIndex - 7) % circleSize
           println("Seven Behind Index: " + sevenBehindIndex)
           val sevenBehindMarble = gameCircle.find(_.getIndex == sevenBehindIndex)
                                             .get
           val (prevMarbles, nextMarbles) = gameCircle.partition(_.getIndex < sevenBehindIndex)
           // TODO: make prettier
           val nextMarblesWithoutSevenBehind = nextMarbles.partition(_.getIndex == sevenBehindIndex)
                                                          ._2
           val nextMarblesUpdated = nextMarblesWithoutSevenBehind
                                               .map(x => x.setIndex(x.getIndex-1))
           val newNextMarbleNumber = nextMarblesUpdated.find(_.getIndex == sevenBehindIndex)
                                                       .get
                                                       .getNumber
           //println("New Next Marble Number: " + newNextMarbleNumber)
           // add the next marble to the current player's collected marble set
           val collectedMarblesUpdated = playerIdToMarbles.getOrElse(curPlayerId, Set()) +
                                         sevenBehindMarble +
                                         new Marble(-1, nextMarbleNumber, Some(curPlayerId))
           return (newNextMarbleNumber,
                   prevMarbles ::: nextMarblesUpdated,
                   playerIdToMarbles + (curPlayerId -> collectedMarblesUpdated))
         }
}





def readInput(filename: String): String = {
  return Source.fromFile(filename)
               .getLines()
               .toList
               .head
}


def parseInput(input: String, magicNumber: Int): Option[Rules] = {
  val numberMatch = "[0-9]+".r
  return numberMatch.findAllIn(input)
                    .toList match {
                     case head :: tail => Some(new Rules(head.toInt, tail.head.toInt, magicNumber))
                     case Nil => None}
}





class Rules(playerCount: Int, marbleCount: Int, magicNumber: Int) {
  def getPlayerCount(): Int = this.playerCount;
  def getMarbleCount(): Int = this.marbleCount;
  def getMagicNumber(): Int = this.magicNumber;

  override def toString(): String = {
    return "(Rules: (PlayerCount = " + this.playerCount +
                  ", MarbleCount = " + this.marbleCount +
                  ", MagicNumber = " + this.magicNumber + "))"
  }
}


class Marble(index: Int, number: Int, ownerId: Option[Int]) {
  def getIndex(): Int = this.index;
  def getNumber(): Int = this.number;
  def getOwnerId(): Option[Int] = this.ownerId;

  def setIndex(newIndex: Int): Marble = {
    return new Marble(newIndex, this.number, this.ownerId)
  }

  def setOwnerId(newOwnerId: Option[Int]): Marble = {
    return new Marble(this.index, this.number, newOwnerId)
  }

  override def toString(): String = {
    return "(Marble# " + this.number + ": (Index = " + this.index + ", OwnerId = " + this.ownerId + "))"
  }

  override def equals(obj: Any): Boolean = obj match {
    case obj: Marble => this.index == obj.getIndex &&
                        this.number == obj.getNumber &&
                        this.ownerId == obj.getOwnerId
    case _ => false
  }

  override def hashCode() = this.index.hashCode + this.number.hashCode + this.ownerId.hashCode
}
