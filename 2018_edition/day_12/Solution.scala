import scala.io.Source



val (initialTunnel, rules) = readInput("input.txt")
val generationCountA = 20

val POT_CHAR_EMPTY = '.'
val POT_CHAR_FULL  = '#'


val endTunnel = solveA(initialTunnel, rules, generationCountA, POT_CHAR_EMPTY)
println("Solution to A: " + endTunnel.getState
                                     .zipWithIndex
                                     .filter(_._1 == POT_CHAR_FULL)
                                     .map(_._2 - endTunnel.getIndex0)
                                     .sum)



def solveA(curTunnel: PotTunnel, rules: Map[String, Char], remainingGenerationCount: Int,
           emptyPotChar: Char): PotTunnel = {
  return remainingGenerationCount match {
          case 0 => curTunnel
          case _ => {
            solveA(curTunnel.spread(rules, emptyPotChar), rules, remainingGenerationCount-1, emptyPotChar)
          }
  }
}





def readInput(filename: String): (PotTunnel, Map[String, Char]) = {
  val lines = Source.fromFile(filename)
                    .getLines
                    .toList
  val initialState = lines.head
                          .split(" ")
                          .apply(2)
  val rules = lines.tail
                   .tail
                   .map(line => line.split(" => "))
                   .map(rule => (rule(0), rule(1).head))
                   .toMap
  return (new PotTunnel(0, initialState), rules)
}





class PotTunnel(index0: Int, state: String) {
  def getIndex0(): Int = this.index0;
  def getState(): String = this.state;

  // Pads the string with dots before spreading, which results in different input
  def spread(rules: Map[String, Char], emptyPotChar: Char): PotTunnel = {
    val (prefixAdded, statePadded, suffixAdded) = PotTunnel.padString(state, emptyPotChar, 5)

    val newState = Range(0, statePadded.size).map(i => {
      val LLCRR = statePadded.slice(i-2, i+3).mkString("")
      rules.getOrElse(LLCRR, emptyPotChar)
    }).mkString("")

    val newIndex0 = index0 + prefixAdded
    return new PotTunnel(newIndex0, newState)
  }

  override def toString(): String = "(" + this.index0 + ", " + this.state.toString + ")"

  override def equals(obj: Any): Boolean = obj match {
    case obj: PotTunnel => this.index0 == obj.getIndex0 &&
                           this.state == obj.getState
    case _ => false
  }

}


object PotTunnel {
  // Pads a string with 5 dots on each side
  // returns the new string as well as how many new dots were added
  def padString(oldString: String, charToPad: Char, paddingSize: Int): (Int, String , Int) = {
    val prefixDotsCount = oldString.takeWhile(_ == charToPad)
                                   .size
    val suffixDotsCount = oldString.reverse
                                   .takeWhile(_ == charToPad)
                                   .size

    val prefixToAdd = if(prefixDotsCount < paddingSize) (paddingSize - prefixDotsCount)
                      else 0
    val suffixToAdd = if(suffixDotsCount < paddingSize) (paddingSize - suffixDotsCount)
                      else 0

    return (prefixToAdd,
            ((charToPad.toString * prefixToAdd) + oldString + (charToPad.toString) * suffixToAdd),
            suffixToAdd)
  }
}
