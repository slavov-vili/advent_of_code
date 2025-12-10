package day08

import java.io.File
import kotlin.math.sqrt
import kotlin.math.pow


data class Position3D(val x: Long, val y: Long, val z: Long) {
    fun calcDist(other: Position3D): Double {
        val distX = (this.x - other.x).toDouble()
        val distY = (this.y - other.y).toDouble()
        val distZ = (this.z - other.z).toDouble()
        return sqrt(distX.pow(2) + distY.pow(2) + distZ.pow(2))
    }
}

data class ClosestTuple(val box1: Position3D, val box2: Position3D, val distance: Double)

data class CircuitMap(val wrapped: Map<Position3D, Long?>, val lastMerged: Pair<Position3D, Position3D>?) {
    val values: Collection<Long?> = this.wrapped.values

    companion object {
        fun of(vararg entries: Pair<Position3D, Long?>): CircuitMap {
            return CircuitMap(mapOf(*entries), null)
        }
    }

    fun getOrDefault(key: Position3D, defaultValue: Long?): Long? {
        return this.wrapped.getOrDefault(key, defaultValue)
    }

    fun containsKey(key: Position3D): Boolean {
        return this.wrapped.containsKey(key)
    }

    operator fun plus(boxPair: Pair<Pair<Position3D, Long?>, Pair<Position3D, Long?>>): CircuitMap {
        return CircuitMap(this.wrapped + listOf(boxPair.first, boxPair.second), Pair(boxPair.first.first, boxPair.second.first))
    }

    fun merge(boxPair: Pair<Pair<Position3D, Long>, Pair<Position3D, Long>>): CircuitMap {
        val (box1, box2) = boxPair
        val mergedCircuit = minOf(box1.second, box2.second)
        return CircuitMap(this.wrapped.mapValues { if (it.value == box1.second || it.value == box2.second) mergedCircuit else it.value },
            Pair(box1.first, box2.first))
    }

    fun isFullyMerged(totalBoxCount: Long): Boolean {
        return this.wrapped.count().toLong() == totalBoxCount && this.wrapped.values.distinct().count() == 1
    }
}

data class CircuitFindingData(val closestTuples: List<ClosestTuple>, val circuitMap: CircuitMap, val i: Long)


fun parseLine(line: String): Position3D {
    val split = line.split(",").map(String::toLong)
    return Position3D(split[0], split[1], split[2])
}

fun <T> findCircuits(closestTuples: List<ClosestTuple>, condition: (CircuitFindingData) -> Boolean,
    resultExtractor: (CircuitFindingData) -> T): T {

    return findCircuits(CircuitFindingData(closestTuples, CircuitMap.of(), 0), condition, resultExtractor)
}

tailrec fun <T> findCircuits(data: CircuitFindingData, condition: (CircuitFindingData) -> Boolean,
    resultExtractor: (CircuitFindingData) -> T): T {
    if (condition(data)) {
        return resultExtractor(data)
    }

    val (curClosestTuples, curCircuitMap, i) = data
    val (minBox1, minBox2) = curClosestTuples[0]

    val circuit1 = curCircuitMap.getOrDefault(minBox1, null)
    val circuit2 = curCircuitMap.getOrDefault(minBox2, i)

    val nextClosestTuples = curClosestTuples.subList(1, curClosestTuples.count() - 1)
    val nextCircuitMap =  if (curCircuitMap.containsKey(minBox1) && curCircuitMap.containsKey(minBox2)) {
        curCircuitMap.merge(Pair(Pair(minBox1, circuit1!!), Pair(minBox2, circuit2!!)))
    } else {
        val circuitId = circuit1 ?: circuit2
        curCircuitMap + Pair(minBox1 to circuitId, minBox2 to circuitId)
    }

    val nextData = CircuitFindingData(nextClosestTuples, nextCircuitMap, i + 1)
    return findCircuits(nextData, condition, resultExtractor)
}

val input = File("input.txt").readLines().filterNot(String::isEmpty).map(::parseLine)

val closestTuples = IntRange(0, input.count() - 2).flatMap { i ->
    IntRange(i + 1, input.count() - 1)
        .map { j ->
            ClosestTuple(input[i], input[j], input[i].calcDist(input[j]))
        }
    }
    .filterNot { it.distance == 0.0 }
    .sortedBy { it.distance }

val circuitCountMap = findCircuits(closestTuples.take(1000), { it.closestTuples.isEmpty() }, { it.circuitMap })
    .values.groupBy { it }
    .mapValues { (_, valueList) -> valueList.count().toLong() }

val sizesMultiplied = circuitCountMap.values.sortedDescending().take(3).reduce { a, b -> a * b }
println("Solution A: $sizesMultiplied")

val fullMergePair = findCircuits(closestTuples, { it.circuitMap.isFullyMerged(input.count().toLong()) }, { it.circuitMap.lastMerged })
val xMultiplied = (fullMergePair?.first?.x ?: 0) * (fullMergePair?.second?.x ?: 0)
println("Solution B: $xMultiplied")
