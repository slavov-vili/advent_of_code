package day07

import java.io.File

typealias Position = Pair<Int, Int>
typealias BeamData = Map<Position, Long>
typealias BeamResult = Pair<BeamData, Set<Position>>

fun Position.travel(): Position {
    return Position(this.first + 1, this.second)
}

fun Position.isRelevant(lastLine: Int): Boolean {
    return this.first in 0..lastLine
}

fun Position.split(): Set<Position> {
    return setOf(Position(this.first, this.second - 1), Position(this.first, this.second + 1))
}

fun BeamData.beams(): Set<Position> {
    return this.keys
}

fun BeamData.timelineCount(beam: Position): Long {
    return this.getOrDefault(beam, 0)
}

fun BeamResult.beamData(): BeamData {
    return this.first
}

fun BeamResult.splitPositions(): Set<Position> {
    return this.second
}

class BeamLaser(val start: Position, val splitterPositions: Set<Position>) {
    val lastLine = splitterPositions.maxOf(Position::first)

    fun analyze(): BeamResult {
        return this.analyzeBeams(BeamResult(mapOf(start to 1), setOf()))
    }

    fun analyzeBeams(prevBeamResult: BeamResult): BeamResult {
        val relevantBeamData = prevBeamResult.beamData().filter { (beam, timelineCount) -> beam.isRelevant(this.lastLine) }
        if (relevantBeamData.isEmpty()) {
            return BeamResult(prevBeamResult.beamData(), prevBeamResult.splitPositions())
        }

        val nextBeamResults: List<BeamResult> = relevantBeamData.map { (beam, timelineCount) -> this.followBeam(beam, timelineCount) }
        val nextBeamData = nextBeamResults.flatMap { it.beamData().toList() }
            .groupBy { it.first }
            .map { (beam, beamDataEntries) -> Pair(beam, beamDataEntries.map { it.second }.sum()) }
            .toMap()
        val nextSplitPositions = nextBeamResults.flatMap(BeamResult::splitPositions).fold(prevBeamResult.splitPositions()) { a, b -> a + b }

        return analyzeBeams(BeamResult(nextBeamData, nextSplitPositions))
    }

    fun followBeam(beam: Position, timelineCount: Long): BeamResult {
        val nextBeam = beam.travel()
        return if (nextBeam in this.splitterPositions) {
            BeamResult(nextBeam.split().associate { it to timelineCount }, setOf(nextBeam))
        } else {
            BeamResult(mapOf(nextBeam to timelineCount), setOf())
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

val analysis = BeamLaser(charPositionMap.get('S')!!.first(), charPositionMap.get('^')!!.toSet()).analyze()

val splitCount = analysis.splitPositions().count()
println("Splits: $splitCount")

val timelineCount = analysis.beamData().values.sum()
println("Timelines: $timelineCount")
