import scala.io.Source


val directions = List(new Direction("west",  (0, -1)),
                      new Direction("north", (-1, 0)),
                      new Direction("east",  (0,  1)),
                      new Direction("south", (1,  0)))
val carSymbolToDirName = Map(('<' -> "west"),
                             ('^' -> "north"),
                             ('>' -> "east"),
                             ('v' -> "south"))

val (initialTrack, initialCars) = separateCars(readInput("input.txt"), carSymbolToDir)




def separateCars(messyTrack: Vector[Vector[Char]], carSymbolToDir: Map[Char, Direction]): (Vector[Vector[Char]], List[Car]) = {

}



class Car(position: Tuple2[Int, Int], direction: Direction, intersectionCount: Int) {
  def getPosition(): Tuple2[Int, Int] = this.position;
  def getDirection(): Direction = this.direction;
  def getIntersectionCount(): Int = this.intersectionCount;

  // TODO: implement
  def move(nextChar: Char): Car = {
    return nextChar match {
      case ''
    }
  }

  override def toString(): String = {
    return "(" + this.position.toString + ", " + this.direction + ", " + this.moveCount.toString + ")"
  }
}


class Direction(name: String, value: Tuple2[Int, Int]) {
  def getName(): String = this.name;
  def getValue(): Tuple2[Int, Int] = this.value;
}





def readInput(filename: String): Vector[Vector[Char]] = {
  return Source.fromFile(filename)
               .getLines
               .map(_.toVector)
               .toVector
}
