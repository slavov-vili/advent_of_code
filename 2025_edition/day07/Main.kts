package day07

import java.io.File

typealias Position = Pair<Int, Int>
typealias BeamResult = Pair<Set<Position>, Set<Position>>

fun Position.travel(): Position {
    return Position(this.first + 1, this.second)
}

fun Position.isRelevant(lastLine: Int): Boolean {
    return this.first in 0..lastLine
}

fun Position.split(): Set<Position> {
    return setOf(Position(this.first, this.second - 1), Position(this.first, this.second + 1))
}

class BeamLaser(val start: Position, val splitterPositions: Set<Position>) {
    val lastLine = splitterPositions.maxOf(Position::first)

    fun countSplits(): Int {
        return this.findSplits(setOf(start), setOf()).count()
    }

    fun findSplits(beams: Set<Position>, pastSplitPositions: Set<Position>): Set<Position> {
        val relevantBeams = beams.filter { it.isRelevant(this.lastLine) }
        if (relevantBeams.isEmpty()) {
            return pastSplitPositions
        }

        val nextBeamResults = relevantBeams.map(this::followBeam)
        val nextBeams = nextBeamResults.flatMap(BeamResult::first).toSet()
        val nextSplitPositions = nextBeamResults.flatMap(BeamResult::second).fold(pastSplitPositions) { a, b -> a + b }
        return findSplits(nextBeams, nextSplitPositions)
    }

    fun followBeam(beam: Position): BeamResult {
        val nextBeam = beam.travel()
        return if (nextBeam in this.splitterPositions) {
            BeamResult(nextBeam.split(), setOf(nextBeam))
        } else {
            BeamResult(setOf(nextBeam), setOf())
        }
    }
}

fun List<String>.charAt(pos: Position): Char {
    return this.get(pos.first)[pos.second]
}

val input = File("input.txt").readLines().filterNot(String::isEmpty)
val charPositionMap = IntRange(0, input.count() - 1)
    .flatMap { line ->
        IntRange(0, input.first().length - 1)
            .map { col -> Position(line, col)
        }
    }
    .filterNot { input.charAt(it) == '.' }
    .groupBy { input.charAt(it) }

val laser = BeamLaser(charPositionMap.get('S')!!.first(), charPositionMap.get('^')!!.toSet())

val splitCount = laser.countSplits()
println("Splits: $splitCount")
