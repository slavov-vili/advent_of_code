import scala.io.Source

// # = wall
// . = space
// G = Goblin
// E = Elf
// game = [round]
// round = aliveUnits.foreach(takeTurn)
// turn order = reading order at beginning of round
//
// turn = move || attack (no diagonals)
//        if(inRange) attack
//        if(existsTarget) move
//        else endTurn
//
// attack = find closest target with lowest health, attack it
//          if (len(reachableTargets) == 0) endTurn
//          if (len(closest) > 1) getFirstInReadingOrder
//          if (len(mostDamaged) > 1) getFirstInReadingOrder
//
//
// move = find closest open square, move towards it
//        (cannot pass through walls or other units)
//        if (len(closest) > 1) getFirstInReadingOrder
//        if (len(nextStep) > 1) getFirstInReadingOrder
//
// startDamage = 3
// startHealth = 200
// if (health <= 0) dead
// if (dead) square = .
//
// FIND: countTurns * totalHP left



val directions = List(new Direction("west",  (0, -1)),
                      new Direction("north", (-1, 0)),
                      new Direction("east",  (0,  1)),
                      new Direction("south", (1,  0)))
val WALL_CHAR = '#'

val input = readInput("input.txt")
val initialBoard = new Battlefield((input.size, input.head.size), WALL_CHAR, getFieldUnits(input))
println(initialBoard)



class Battlefield(dimensions: Tuple2[Int, Int], wallChar: Char, fieldUnits: Vector[FieldUnit]) {
  def getWallChar(): Char = this.wallChar;
  def getDimensions(): Tuple2[Int, Int] = this.dimensions;
  def getWallUnits(): Vector[FieldUnit] = this.fieldUnits
                                              .filter(_.getType == this.wallChar);
  def getLivingUnits(): Vector[LivingUnit] = this.fieldUnits
                                                 .filterNot(_.getType == this.wallChar)
                                                 .map(_.asInstanceOf[LivingUnit])
  def getOccupiedCoords(): Vector[Tuple2[Int, Int]] = this.fieldUnits.map(_.getCoords)

  // TODO: implement
  //def doBattle(): 

  // TODO: implement
  //def doRound(): 


  override def toString(): String = {
    return "(Battlefield:\n Dimensions: " + this.dimensions.toString + "\n WallChar: " + this.wallChar.toString +
           "\n FieldUnits:\n" + this.fieldUnits.map(_.toString + "\n") + ")"
  }
}



class LivingUnit(unitType: Char, coords: Tuple2[Int, Int], damage: Int, health: Int)
extends FieldUnit(unitType, coords) {
  def getDamage(): Int = this.damage;
  def getHealth(): Int = this.health;

  // TODO: implement
  def takeTurn():

  override def toString(): String = {
    return "(" + this.unitType.toString + ", " + this.coords.toString + ", " +
           this.damage.toString + ", " + this.health.toString + ")"
  }
}


class FieldUnit(unitType: Char, coords: Tuple2[Int, Int]) {
  def getType(): Char = this.unitType;
  def getCoords(): Tuple2[Int, Int] = this.coords;

  override def toString(): String = {
    return "(" + this.unitType.toString + ", " + this.coords.toString + ")"
  }
}



class Direction(name: String, value: Tuple2[Int, Int]) {
  def getName(): String = this.name;
  def getValue(): Tuple2[Int, Int] = this.value;

  override def toString(): String = {
    return "(" + this.getName + ": " + this.getValue.toString + ")"
  }
}





def getFieldUnits(input: Vector[Vector[Char]]): Vector[FieldUnit] = {
  return Range(0, input.size).flatMap(x => 
          Range(0, input.head.size).map(y =>
            createUnit(input(x)(y), (x, y))
         ))
           .collect({ case Some(x) => x })
         .toVector
}


def createUnit(unitType: Char, coords: Tuple2[Int, Int]): Option[FieldUnit] = {
  return unitType match {
    // it's a wall
    case '#' => Some(new FieldUnit(unitType, coords))
    case 'G' | 'E' => Some(new LivingUnit(unitType, coords, 3, 200))
    case _ => None
  }
}


def readInput(filename: String): Vector[Vector[Char]] = {
  return Source.fromFile(filename)
               .getLines
               .map(_.toVector)
               .toVector
}
