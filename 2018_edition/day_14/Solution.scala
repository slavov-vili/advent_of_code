val initialBoard = Vector(3, 7)
val initialElves = List(new Elf(0), new Elf(1))

val recipeCount = 939601


println("Solution to A: " + solveA(initialBoard, recipeCount, initialElves, 10).mkString(""))



def solveA(curScoreboard: Vector[Int], requiredRecipes: Int, curElves: List[Elf], lookahead: Int): Vector[Int] = {
  //println("Board: " + curScoreboard)
  //println(curElves)
  return if(curScoreboard.size >= (requiredRecipes + lookahead)) {
           curScoreboard.drop(requiredRecipes)
            .take(lookahead)
         } else {
           val nextRecipesInt = curElves.map(x => curScoreboard(x.getRecipeIndex))
                                        .sum
           val nextRecipes = nextRecipesInt.toString
                                           .split("")
                                           .map(_.toInt)
           val updatedScoreboard = curScoreboard ++ nextRecipes
           val updatedElves = curElves.map(_.updateRecipeIndex(updatedScoreboard))
           solveA(updatedScoreboard, requiredRecipes, updatedElves, lookahead)
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
