package day04

import java.io.File

typealias Direction = Pair<Int, Int>
typealias Position = Pair<Int, Int>
typealias Limits = Pair<IntRange, IntRange>
typealias PaperRollMap = List<String>

val directions: List<Direction> = listOf(Direction(-1, 0), Direction(-1, 1), Direction(0, 1), Direction(1, 1),
    Direction(1, 0), Direction(1, -1), Direction(0, -1), Direction(-1, -1))

fun Position.move(other: Direction): Position {
    return Position(this.first + other.first, this.second + other.second)
}

fun Position.isValid(limits: Limits): Boolean {
    return this.first in limits.first && this.second in limits.second
}

fun Position.getValidNeighbors(rollMap: PaperRollMap, all: Boolean): List<Position> {
    val limits = rollMap.getLimits()
    val step = if (all) 1 else 2
    return IntRange(0, directions.count() - 1).step(step)
        .map { this.move(directions[it]) }
        .filter { it.isValid(limits) }
}

fun Position.isAccessible(rollMap: PaperRollMap): Boolean {
    return this.getValidNeighbors(rollMap, true)
        .filter { rollMap.isRoll(it) }
        .count() < 4
}

fun PaperRollMap.isRoll(pos: Position): Boolean {
    return this[pos.first][pos.second] == '@'
}

fun PaperRollMap.unsetAll(positions: List<Position>): PaperRollMap {
    if (positions.isEmpty())
        return this

    val tempRollMap = this.toMutableList()
    for (pos in positions) {
        tempRollMap[pos.first] = tempRollMap[pos.first].replaceRange(pos.second, pos.second + 1, ".")
    }
    return tempRollMap.toList()
}

fun PaperRollMap.getLimits(): Limits {
    return Limits(IntRange(0, this.count() - 1), IntRange(0, this[0].length - 1))
}

fun PaperRollMap.getPositions(): List<Position> {
    val limits = this.getLimits()
    return IntRange(0, limits.second.endInclusive)
        .flatMap { col ->
            IntRange(0, limits.first.endInclusive)
            .map { line -> Position(line, col) } }
}

fun PaperRollMap.findAccessibleRollPositions(): List<Position> {
    return this.getPositions().filter { this.isRoll(it) && it.isAccessible(this) }
}

fun PaperRollMap.countAllRemovable(): Int {
    val accessible = this.findAccessibleRollPositions()
    if (accessible.isEmpty())
        return 0;

    val newRollMap = this.unsetAll(accessible)
    return accessible.count() + newRollMap.countAllRemovable()
}

val input: PaperRollMap = File("input.txt").readLines().filterNot(String::isEmpty)

val accessible = input.findAccessibleRollPositions()
println("Accessible rolls: ${accessible.count()}")

val allRemovable = input.countAllRemovable()
println("All removable rolls: $allRemovable")
