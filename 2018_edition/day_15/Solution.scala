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



val input = readInput("input.txt")





def readInput(filename: String): Vector[Vector[Char]] = {
  return Source.fromFile(filename)
               .getLines
               .map(_.toVector)
               .toVector
}
