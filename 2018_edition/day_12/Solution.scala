import scala.io.Source



val POT_CHAR_EMPTY = '.'
val POT_CHAR_FULL  = '#'

val (initialTunnel, rules) = readInput("input.txt", POT_CHAR_EMPTY, 5)
val generationCountA = 20
val generationCountB = 50000000000L


val endTunnelA = solveA(initialTunnel, rules, generationCountA, POT_CHAR_EMPTY)
println("Solution to A: " + endTunnelA.calcPotScore(POT_CHAR_FULL))
val endScoreB = solveB(initialTunnel, rules, Map[String, PotTunnel](), generationCountB, POT_CHAR_EMPTY, POT_CHAR_FULL)
println("Solution to B: " + endScoreB)



def solveA(curTunnel: PotTunnel, rules: Map[String, Char], remainingGenerationCount: Int,
           emptyPotChar: Char): PotTunnel = {
  return remainingGenerationCount match {
          case 0 => curTunnel
          case _ => {
            solveA(curTunnel.spread(rules, emptyPotChar), rules, remainingGenerationCount-1, emptyPotChar)
          }
  }
}


def solveB(curTunnel: PotTunnel, rules: Map[String, Char], seenStates: Map[String, PotTunnel],
           generationCount: Long, emptyPotChar: Char, fullPotChar: Char): Long = {
  println("Seen states: " + seenStates.size)
  val (prefixRemoved, curTunnelStateTrimmed, suffixRemoved) = PotTunnel.trimState(curTunnel.getState, emptyPotChar)
  return seenStates.contains(curTunnelStateTrimmed) match {
    case false => solveB(curTunnel.spread(rules, emptyPotChar), rules,
                         seenStates + (curTunnelStateTrimmed -> curTunnel),
                         generationCount, emptyPotChar, fullPotChar)
    case true => {
      val curStateScore = curTunnel.calcPotScore(fullPotChar)
      val repeatedStateScore = seenStates(curTunnelStateTrimmed).calcPotScore(fullPotChar)
      //println("Repeating pattern (trimmed): " + curTunnelStateTrimmed)
      //println("Repeating State: " + seenStates(curTunnelStateTrimmed))
       curStateScore.toLong + ((generationCount - seenStates.size) * (curStateScore - repeatedStateScore)).toLong
    }
  }
}





def readInput(filename: String, emptyPotChar: Char, paddingSize: Int): (PotTunnel, Map[String, Char]) = {
  val lines = Source.fromFile(filename)
                    .getLines
                    .toList
  val initialState = lines.head
                          .split(" ")
                          .apply(2)
  // put padding on the initial state
  val (prefixAdded, initialStatePadded, suffixAdded) = PotTunnel.padState(initialState, emptyPotChar, paddingSize)
  val rules = lines.tail
                   .tail
                   .map(line => line.split(" => "))
                   .map(rule => (rule(0), rule(1).head))
                   .toMap
  return (new PotTunnel(prefixAdded, initialStatePadded, paddingSize), rules)
}





class PotTunnel(index0: Int, state: String, paddingSize: Int) {
  def getIndex0(): Int = this.index0;
  def getState(): String = this.state;
  def getPaddingSize(): Int = this.paddingSize;

  // Pads the string with dots before spreading, which results in different input
  def spread(rules: Map[String, Char], emptyPotChar: Char): PotTunnel = {
    val (prefixAdded, statePadded, suffixAdded) = PotTunnel.padState(state, emptyPotChar, paddingSize)

    val newState = Range(0, statePadded.size).map(i => {
      val LLCRR = statePadded.slice(i-2, i+3).mkString("")
      rules.getOrElse(LLCRR, emptyPotChar)
    }).mkString("")

    val newIndex0 = index0 + prefixAdded
    return new PotTunnel(newIndex0, newState, this.paddingSize)
  }

  def calcPotScore(fullPotChar: Char): Int = {
    return this.state
               .zipWithIndex
               .filter(_._1 == fullPotChar)
               .map(_._2 - this.index0)
               .sum
  }

  override def toString(): String = "(" + this.index0 + ", " + this.state.toString + ")"

  override def equals(obj: Any): Boolean = obj match {
    case obj: PotTunnel => this.index0 == obj.getIndex0 &&
                           this.state == obj.getState &&
                           this.paddingSize == obj.getPaddingSize
    case _ => false
  }

}


object PotTunnel {
  // Pads a string with 5 dots on each side
  // returns the new string as well as how many new dots were added
  def padState(oldState: String, charToPad: Char, paddingSize: Int): (Int, String , Int) = {
    val prefixDotsCount = oldState.takeWhile(_ == charToPad)
                                  .size
    val suffixDotsCount = oldState.reverse
                                  .takeWhile(_ == charToPad)
                                  .size

    val prefixToAdd = if(prefixDotsCount < paddingSize) (paddingSize - prefixDotsCount)
                      else 0
    val suffixToAdd = if(suffixDotsCount < paddingSize) (paddingSize - suffixDotsCount)
                      else 0

    return (prefixToAdd,
            ((charToPad.toString * prefixToAdd) + oldState + (charToPad.toString * suffixToAdd)),
            suffixToAdd)
  }


  def trimState(oldState: String, charToTrim: Char): (Int, String, Int) = {
    val prefixDotsCount = oldState.takeWhile(_ == charToTrim)
                                  .size
    val suffixDotsCount = oldState.reverse
                                  .takeWhile(_ == charToTrim)
                                  .size

    return (prefixDotsCount,
            oldState.take(oldState.length - suffixDotsCount)
                    .drop(prefixDotsCount),
            suffixDotsCount)
  }
}
