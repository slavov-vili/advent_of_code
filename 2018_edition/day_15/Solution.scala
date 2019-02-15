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
val fieldUnits = getFieldUnits(input, WALL_CHAR)
val initialBoard = new Battlefield((input.size, input.head.size), WALL_CHAR, fieldUnits)
println(initialBoard)



class Battlefield(dimensions: Tuple2[Int, Int], wallChar: Char, fieldUnits: Vector[FieldUnit]) {
  def getWallChar(): Char = this.wallChar;
  def getDimensions(): Tuple2[Int, Int] = this.dimensions;
  def getSize(): Int = this.dimensions._1 * this.dimensions._2;
  def getWallUnits(): Vector[FieldUnit] = this.fieldUnits
                                              .filter(_.getType == this.wallChar)
  def getLivingUnits(): Vector[LivingUnit] = this.fieldUnits
                                                 .filterNot(_.getType == this.wallChar)
                                                 .map(_.asInstanceOf[LivingUnit])
  def getFieldUnits(): Vector[FieldUnit] = this.fieldUnits;

  def setFieldUnits(newFieldUnits: Vector[FieldUnit]): Battlefield = {
    new Battlefield(this.dimensions, this.wallChar, newFieldUnits)
  }


  // TODO: implement
  //def doBattle(): 

  // TODO: figure out how to deal with dead units
  // to constantly update the battlefield after each unit's turn - use foldLeft
  def doRound(): Vector[FieldUnit] = {
    val unitsInitialOrder = this.getLivingUnits
                                .sortBy(_.getCoords)

    return unitsInitialOrder.foldLeft(this.fieldUnits)((allUnits, curUnit) =>
        allUnits.find(_.equals(curUnit)) match {
          // The current unit is still ALIVE
          case Some(unit) => {
            val livingUnit = unit.asInstanceOf[LivingUnit]
            val unitAction = livingUnit.determineAction(this.fieldUnits)
            this.handleAction(livingUnit, unitAction)
          }
          // The current unit is DEAD
          case _ => this.fieldUnits
        }
    )

  }


  // TODO: extract bracket stuff into separate functions?
  private def handleAction(unit: LivingUnit, action: Action): Vector[FieldUnit] = {
    return action.getName match {
      case Action.ACTIONNAME_ATTACK => {
        val enemyUnitIndex = this.fieldUnits.indexWhere(_.getCoords.equals(action.getCoords))
        val oldEnemyUnit = this.fieldUnits(enemyUnitIndex)
        // Can't attack walls/empty space AND can't attack allies
        val newEnemyUnit = if(oldEnemyUnit.isInstanceOf[LivingUnit] &&
                             !oldEnemyUnit.getType.equals(unit.getType)) {
                               oldEnemyUnit.asInstanceOf[LivingUnit].adjustHealth(-unit.getDamage)
                             }
                           else oldEnemyUnit
        if(newEnemyUnit.getType.equals(FieldUnit.UNITTYPE_DEAD)) this.fieldUnits.filter(_.equals(oldEnemyUnit))
        else this.fieldUnits.updated(enemyUnitIndex, newEnemyUnit)
      }
      case Action.ACTIONNAME_MOVE => {
        val unitIndex = this.fieldUnits.indexWhere(_.equals(unit))
        val newUnit = unit.setCoords(action.getCoords)
        this.fieldUnits.updated(unitIndex, newUnit)
      }
      case _ => this.fieldUnits
    }
  }


  override def toString(): String = {
    return "(Battlefield:\n" +
           "  Dimensions: " + this.dimensions.toString + "\n" +
           "  WallChar:   " + this.wallChar.toString   + "\n" +
           "  WallUnits:\n  " + this.getWallUnits.mkString("\n")   + "\n" +
           "  LivingUnits:\n" + this.getLivingUnits.mkString("\n") + ")"
  }
}



class LivingUnit(unitType: Char, coords: Tuple2[Int, Int], damage: Int, health: Int)
extends FieldUnit(unitType, coords) {
  def getDamage(): Int = this.damage;
  def getHealth(): Int = this.health;

  def setHealth(newHealth: Int): LivingUnit = {
    // change the type if the unit dies
    val newType = if(newHealth <= 0) FieldUnit.UNITTYPE_DEAD
                  else this.unitType
    return new LivingUnit(newType, this.coords, this.damage, newHealth)
  }

  def adjustHealth(hpAdjust: Int): LivingUnit = {
    return this.setHealth(this.health + hpAdjust)
  }

  // TODO: implement
  def determineAction(allUnits: Vector[FieldUnit]): Action = {
    return null
  }

  override def toString(): String = {
    return "(" + this.unitType.toString + ", " + this.coords.toString + ", " +
           this.damage.toString + ", " + this.health.toString + ")"
  }
}


class FieldUnit(unitType: Char, coords: Tuple2[Int, Int]) {
  def getType(): Char = this.unitType;
  def getCoords(): Tuple2[Int, Int] = this.coords;

  def setType(newType: Char): FieldUnit = {
    new FieldUnit(newType, this.coords)
  }

  def setCoords(newCoords: Tuple2[Int, Int]) = {
    new FieldUnit(this.unitType, newCoords)
  }

  override def equals(that: Any): Boolean = {
    that match {
      case that: FieldUnit => this.getCoords.equals(that.getCoords) &&
                              this.getType.equals(that.getType)
      case _ => false
    }
  }

  override def toString(): String = {
    return "(" + this.unitType.toString + ", " + this.coords.toString + ")"
  }
}


object FieldUnit {
  val UNITTYPE_WALL = '#'
  val UNITTYPE_EMPTY = '.'
  val UNITTYPE_ELF = 'E'
  val UNITTYPE_GOBLIN = 'G'
  val UNITTYPE_DEAD = 'X'
}


class Action(name: String, coords: Tuple2[Int, Int]) {
  def getName(): String = this.name;
  def getCoords(): Tuple2[Int, Int] = this.coords;

  override def toString(): String = {
    return "(" + this.name.toString + ": " + this.coords.toString + ")"
  }
}

object Action {
  val ACTIONNAME_MOVE = "move";
  val ACTIONNAME_ATTACK = "attack";
  val ACTIONNAME_STAY = "stay";
}



class Direction(name: String, value: Tuple2[Int, Int]) {
  def getName(): String = this.name;
  def getValue(): Tuple2[Int, Int] = this.value;

  override def toString(): String = {
    return "(" + this.name + ": " + this.value.toString + ")"
  }
}





def getFieldUnits(input: Vector[Vector[Char]], wallChar: Char): Vector[FieldUnit] = {
  val fieldUnits =  Range(0, input.size).flatMap(x => 
                      Range(0, input.head.size).map(y =>
                        createUnit(input(x)(y), (x, y))
                    ))
                    .collect({ case Some(x) => x })
                    .toVector

  return fieldUnits
}


def createUnit(unitType: Char, coords: Tuple2[Int, Int]): Option[FieldUnit] = {
  return unitType match {
    // it's a WALL
    case '#' => Some(new FieldUnit(unitType, coords))
    // it's a CREATURE
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
