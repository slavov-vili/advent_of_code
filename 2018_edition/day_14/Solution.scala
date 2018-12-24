val initialScoreboard = new Scoreboard(Vector(3, 7),
                                       List(new Elf(0), new Elf(1)))

val inputA = 939601
val solutionA = solveA(initialScoreboard, inputA, 10)
println("Solution to A: " + solutionA.mkString(""))


val inputB = inputA.toString
                   .split("")
                   .map(_.toInt)
                   .toList
val solutionB = solveB(initialScoreboard, inputB, 100000)
println("Solution to B: " + solutionB.size)



def solveA(curScoreboard: Scoreboard, requiredRecipes: Int, lookahead: Int): Vector[Int] = {
  //println("Board: " + curScoreboard)
  //println(curElves)
  return if(curScoreboard.getScores.size >= (requiredRecipes + lookahead)) {
           curScoreboard.getScores
                        .drop(requiredRecipes)
                        .take(lookahead)
         } else {
           solveA(curScoreboard.update, requiredRecipes, lookahead)
         }
}


def solveB(curScoreboard: Scoreboard, relevantRecipeValues: List[Int], batchSize: Int): Vector[Int] = {
  //println("Board: " + curScoreboard.getScores.size)
  //println(curElves)
  val updatedScoreboard = curScoreboard.update(batchSize)
  val inputFoundAt = updatedScoreboard.getScores.indexOfSlice(relevantRecipeValues)
  return if(inputFoundAt == -1) {
           solveB(updatedScoreboard, relevantRecipeValues, batchSize)
         }
         else updatedScoreboard.getScores.take(inputFoundAt)
}





class Scoreboard(scores: Vector[Int], elves: List[Elf]) {
  def getScores(): Vector[Int] = this.scores;
  def getElves(): List[Elf] = this.elves;

  def update(): Scoreboard = {
    val nextRecipesInt = this.elves.map(x => this.scores(x.getRecipeIndex))
                                   .sum
    val nextRecipeScores = nextRecipesInt.toString
                                         .split("")
                                         .map(_.toInt)
    val updatedScores = this.scores ++ nextRecipeScores
    val updatedElves = this.elves.map(_.updateRecipeIndex(updatedScores))
    return new Scoreboard(updatedScores, updatedElves)
  }

  def update(batchSize: Int): Scoreboard = {
    return Range(0, batchSize).foldLeft(this)((tempScoreboard, i) => tempScoreboard.update)
  }

  override def toString(): String = {
    return "(Elves:\n" + this.elves.toString + "\nScores:\n" + this.scores.toString + ")"
  }
}


class Elf(recipeIndex: Int) {
  def getRecipeIndex(): Int = this.recipeIndex;

  def updateRecipeIndex(updatedScoreboard: Vector[Int]): Elf = {
    val curRecipeValue = updatedScoreboard(this.recipeIndex)
    val nextRecipeIndex = (recipeIndex + curRecipeValue + 1) % updatedScoreboard.size
    return new Elf(nextRecipeIndex)
  }

  override def toString(): String = {
    return "(" + this.recipeIndex.toString + ")"
  }
}
