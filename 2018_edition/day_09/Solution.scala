import scala.io.Source

val input = readInput("input.txt")

val rules = parseInput(input, 23).get

val initialSetup = new GameSetup(0, List(new Marble(0, None)))

//println("Solution to A: " + solveA(initialSetup, rules))

println("Solution to B: " + solveB(initialSetup, rules))

def solveA(initialSetup: GameSetup, rules: Rules): Int = {
  val (endCircle, endResults) = playGame(rules, initialSetup, 0, Map())
  println("Results: " + endResults.size)
  endResults.foreach(println)

  return endResults.map(x => x._2.map(_.getNumber).sum)
                   .max
}



def playGame(rules: Rules, curGameSetup: GameSetup,
             curPlayerId: Int, playerIdToMarbles: Map[Int, Set[Marble]]):
            (List[Marble], Map[Int, Set[Marble]]) = {
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

  val totalMarbleCount = curGameSetup.getGameCircle.size + playerIdToMarbles.flatMap(_._2).size
  //println("Total Marble Count: " + totalMarbleCount)

  return if(totalMarbleCount > rules.getMarbleCount) (curGameSetup.getGameCircle, playerIdToMarbles)
         else {
           println("Current Marble: " + curGameSetup.getCurMarble)
           val (newGameSetup, newScores) =
               takeTurn(rules, curGameSetup, curPlayerId, playerIdToMarbles)
           playGame(rules, newGameSetup,
                    (curPlayerId + 1) % rules.getPlayerCount, newScores)
         }
}


def takeTurn(rules: Rules, curGameSetup: GameSetup,
             curPlayerId: Int, playerIdToMarbles: Map[Int, Set[Marble]]):
            (GameSetup, Map[Int, Set[Marble]]) = {
  val lastMarbleIndex = curGameSetup.getCurMarbleIndex
  val gameCircle = curGameSetup.getGameCircle
  //println(curPlayerId + "'s turn")
  val circleSize = gameCircle.size
  // Next marble = # current marbles in the field + # collected marbles
  val nextMarbleNumber = circleSize + playerIdToMarbles.flatMap(_._2)
                                                       .size
  //println("Circle Size: " + circleSize)
  //println("Collected Marbles: " + playerIdToMarbles.flatMap(_._2).size)
  //println("Current Marble Index: " + lastMarbleIndex)
  //println("Next Marble Number : " + nextMarbleNumber)
  return if(nextMarbleNumber % rules.getMagicNumber != 0) {
           val newGameSetup = curGameSetup.addToCircle(nextMarbleNumber)
           return (newGameSetup,
                   playerIdToMarbles)
         } else {
           val (newGameSetup, removedMarbles) = curGameSetup.removeFromCircle(nextMarbleNumber)
           // add the removed marbles to the current player's collected marble set
           val newCollectedMarbles = playerIdToMarbles.getOrElse(curPlayerId, Set()) ++
                                     removedMarbles.map(_.setOwnerId(Some(curPlayerId)))
           return (newGameSetup,
                   playerIdToMarbles + (curPlayerId -> newCollectedMarbles))
         }
}


// tries to process the game in batches of 23
// instead of generating the whole circle, it only generates
// the 23rd stage
def solveB(initialSetup: GameSetup, rules: Rules): Int = {
  val (endSetup, collectedMarbles) = simulateGame(initialSetup: GameSetup, rules, 1, Set())

  return collectedMarbles.groupBy(x => Some(x.getOwnerId))
                         .mapValues(_.map(x => x.getNumber).sum)
                         .maxBy(_._2)
                         ._2
}


def simulateGame(curGameSetup: GameSetup, rules: Rules, curBatchIndex: Int,
                 collectedMarbles: Set[Marble]): (GameSetup, Set[Marble]) = {
  val significantMarbleNumber = curBatchIndex * rules.getMagicNumber

  return if(significantMarbleNumber > rules.getMarbleCount) (curGameSetup, collectedMarbles)
         else {
           val significantPlayerId = (significantMarbleNumber % rules.getPlayerCount) - 1
           val (newGameSetup, newCollectedMarbles) =
               processStage(curGameSetup, curGameSetup.getCircleSize + collectedMarbles.size,
                            significantPlayerId, significantMarbleNumber)
           return simulateGame(newGameSetup, rules, curBatchIndex+1, collectedMarbles ++ newCollectedMarbles)
         }
}


// TODO: keep track of deleted marbles (maybe pass previous setup?)
def processStage(curGameSetup: GameSetup, nextMarbleNumber: Int, significantPlayerId: Int,
                 significantMarbleNumber: Int): (GameSetup, Set[Marble]) = {
  //println("Current Setup: " + curGameSetup)
  println("Current Significant Marble ID: " + significantMarbleNumber)
  val newCurGameSetup = Range(nextMarbleNumber, significantMarbleNumber)
                        .foldLeft(curGameSetup)((nextSetup, x) => nextSetup.addToCircle(x))
  //println("Stage:")
  //println("Significant Marble: " + significantMarbleNumber)
  //println("Significant Player: " + significantPlayerId)
  //newCurGameSetup.getGameCircle.foreach(println)
  val (nextGameSetup, collectedMarbles) = newCurGameSetup.removeFromCircle(significantMarbleNumber)

  return (nextGameSetup, collectedMarbles.map(_.setOwnerId(Some(significantPlayerId))))
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


class GameSetup(curMarbleIndex: Int, gameCircle: List[Marble]) {
  def getCurMarbleIndex(): Int = this.curMarbleIndex;
  def getGameCircle(): List[Marble] = this.gameCircle;

  def getCurMarble(): Marble = this.gameCircle(this.curMarbleIndex)

  def getCircleSize(): Int = this.gameCircle.size

  def addToCircle(nextMarbleNumber: Int): GameSetup = {
    val nextMarbleIndex = ((this.curMarbleIndex + 1) % this.gameCircle.size) + 1
    //println("Next Marble Index : " + nextMarbleIndex)
    val nextMarble = new Marble(nextMarbleNumber, None)
    val (prevMarbles, nextMarbles) = this.gameCircle.splitAt(nextMarbleIndex)
  
    return new GameSetup(nextMarbleIndex, prevMarbles ++ List(nextMarble) ++ nextMarbles)
  }
  
  
  def removeFromCircle(nextMarbleNumber: Int):
                      (GameSetup, Set[Marble]) = {
    val nextMarble = new Marble(nextMarbleNumber, None)
    // should take care of the cases where curIndex < 7
    val sevenBackwardsIndex = (this.gameCircle.size + this.curMarbleIndex - 7) % this.gameCircle.size
    //println("Index 7 Marbles Counter-Clockwise: " + sevenBackwardsIndex)
    val sevenBackwardsMarble = this.gameCircle(sevenBackwardsIndex)
    val (prevMarbles, nextMarbles) = this.gameCircle.splitAt(sevenBackwardsIndex)
    //println("New Next Marble Index: " + sevenBackwardsIndex)
  
    return (new GameSetup(sevenBackwardsIndex, prevMarbles ++ nextMarbles.tail), Set(nextMarble, sevenBackwardsMarble))
  }

  override def toString(): String = "(" + this.curMarbleIndex + " : " + this.gameCircle + ")"
}


class Marble(number: Int, ownerId: Option[Int]) {
  def getNumber(): Int = this.number;
  def getOwnerId(): Option[Int] = this.ownerId;

  def setOwnerId(newOwnerId: Option[Int]): Marble = {
    return new Marble(this.number, newOwnerId)
  }

  override def toString(): String = {
    return "(Marble# " + this.number + ": OwnerId = " + this.ownerId + ")"
  }

  override def equals(obj: Any): Boolean = obj match {
    case obj: Marble => this.number == obj.getNumber &&
                        this.ownerId == obj.getOwnerId
    case _ => false
  }

  override def hashCode() = this.number.hashCode + this.ownerId.hashCode
}
