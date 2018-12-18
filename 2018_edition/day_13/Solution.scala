import scala.io.Source


val directions = List(new Direction("west",  (0, -1)),
                      new Direction("north", (-1, 0)),
                      new Direction("east",  (0,  1)),
                      new Direction("south", (1,  0)))
val carSymbolToDirectionName = Map(('<' -> "west"),
                                   ('^' -> "north"),
                                   ('>' -> "east"),
                                   ('v' -> "south"))

val (initialTrack, initialCars) = separateCars(readInput("input.txt"), carSymbolToDirectionName, directions)
println("Initial cars: " + initialCars)

val (finalCarsA, crashesA) = solveA(initialCars, List[Tuple2[Int, Int]](), directions, initialTrack)
println("Solution to A: " + (crashesA.head._2, crashesA.head._1))


def solveA(curCars: List[Car], curCrashes: List[Tuple2[Int, Int]], directions: List[Direction],
           track: Vector[Vector[Char]]): (List[Car], List[Tuple2[Int, Int]]) = {
  // sort the current cars before moving them (easier to detect earlier crashes)
  val (movedCars, crashes) = findCrashes(curCars.sortBy(_.getPosition), track, directions)
  //println("Moved cars: " + movedCars)

  return if(crashes.size == 0) solveA(movedCars, crashes, directions, track)
         else {
           // reverse the crashes, since they were prepended
           (movedCars, crashes)
         }
}


// move each car and check if it results in a crash
def findCrashes(curCars: List[Car], track: Vector[Vector[Char]], directions: List[Direction]):
               (List[Car], List[Tuple2[Int, Int]]) = {
  val (movedCars, crashes) = curCars.zipWithIndex
      .foldLeft((curCars, List[Tuple2[Int, Int]]()))(
        (carsCrashes, carIdx) => {
        val movedCar = carIdx._1.move(track, directions)
        val newCrashes = carsCrashes._2 ++ carsCrashes._1
                                           .map(_.getPosition)
                                           .filter(_ == movedCar.getPosition)
        (carsCrashes._1.updated(carIdx._2, movedCar), newCrashes)
      })
  return (movedCars, crashes)
}





// replace all cars in the track with dots
def separateCars(messyTrack: Vector[Vector[Char]], carSymbolToDirectionName: Map[Char, String],
                 directions: List[Direction]): (Vector[Vector[Char]], List[Car]) = {
  val trackCoords = Range(0, messyTrack.size).flatMap(x =>
                    Range(0, messyTrack.head.size).map(y => (x, y)))
                    .toList

  val carCoords = trackCoords.filter(xy => carSymbolToDirectionName.contains(messyTrack(xy._1)(xy._2)))

  val cleanTrack = carCoords.foldLeft(messyTrack)((track, coord) =>
                    track.updated(coord._1,
                      track(coord._1).updated(coord._2, '.')))

  val carObjects = carCoords.map(xy => {
    val curDirectionName = carSymbolToDirectionName(messyTrack(xy._1)(xy._2))
    new Car(xy, directions.find(_.getName == curDirectionName).get, 0)
  })

  return (cleanTrack, carObjects)
}



class Car(position: Tuple2[Int, Int], direction: Direction, intersectionCount: Int) {
  def getPosition(): Tuple2[Int, Int] = this.position;
  def getDirection(): Direction = this.direction;
  def getIntersectionCount(): Int = this.intersectionCount;


  def move(track: Vector[Vector[Char]], directions: List[Direction]): Car = {
    val nextPosition = (this.position._1 + this.direction.getValue._1,
                        this.position._2 + this.direction.getValue._2)
    val nextChar = track(nextPosition._1)(nextPosition._2)
    
    val nextDirectionValue = nextChar match {
      // next direction = switch x and y
      case '\\' => (this.direction.getValue._2, this.direction.getValue._1)
      // next direction = switch x and y and * -1
      case '/' => (this.direction.getValue._2 * (-1), this.direction.getValue._1 * (-1))
      case '+' => calcIntersectionDirectionValue(this.direction.getValue, this.intersectionCount+1)
      case  _  => this.direction.getValue
    }
    val nextDirection = directions.find(_.getValue == nextDirectionValue)
                                  .get

    val nextIntersectionCount = if(nextChar == '+')
                                  this.intersectionCount + 1
                                else this.intersectionCount

    return new Car(nextPosition, nextDirection, nextIntersectionCount)
  }


  private def calcIntersectionDirectionValue(curDirectionValue: Tuple2[Int, Int],
                                             intersectionCount: Int): Tuple2[Int, Int] = {
    return intersectionCount match {
      case x if x % 3 == 1 => (curDirectionValue._2 * (-1), curDirectionValue._1)
      case x if x % 3 == 2 => curDirectionValue
      case x if x % 3 == 0 => (curDirectionValue._2, curDirectionValue._1 * (-1))
    }
  }


  override def toString(): String = {
    return "(" + this.position.toString + ", " + this.direction + ", " + this.intersectionCount.toString + ")"
  }
}



class Direction(name: String, value: Tuple2[Int, Int]) {
  def getName(): String = this.name;
  def getValue(): Tuple2[Int, Int] = this.value;

  override def toString(): String = {
    return "(" + this.getName + ": " + this.getValue.toString + ")"
  }
}





def readInput(filename: String): Vector[Vector[Char]] = {
  return Source.fromFile(filename)
               .getLines
               .map(_.toVector)
               .toVector
}
