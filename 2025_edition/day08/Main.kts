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

data class CircuitMap(val wrapped: Map<Position3D, Long?>) {
    val values = this.wrapped.values

    companion object {
        fun of(vararg entries: Pair<Position3D, Long?>): CircuitMap {
            return CircuitMap(mapOf(*entries))
        }
    }

    // TODO: getOrDefault, containsKey, plus, merge
    fun getOrDefault(key: Position3D, defaultValue: Long?): Long? {
        return this.wrapped.getOrDefault(key, defaultValue)
    }

    fun containsKey(key: Position3D): Boolean {
        return this.wrapped.containsKey(key)
    }

    operator fun plus(other: CircuitMap): CircuitMap {
        return CircuitMap(this.wrapped + other.wrapped)
    }

    fun merge(circuits: Set<Long>): CircuitMap {
        val mergedCircuit = circuits.minOf { it }
        return CircuitMap(this.wrapped.mapValues { if (circuits.contains(it.value)) mergedCircuit else it.value })
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
    val (curClosestTuples, curCircuitMap, i) = data
    if (condition(data)) {
        return resultExtractor(data)
    }

    val (minBox1, minBox2) = curClosestTuples[0]

    val circuit1 = curCircuitMap.getOrDefault(minBox1, null)
    val circuit2 = curCircuitMap.getOrDefault(minBox2, i)
    // println("\nMin pair: $minBox1 -> $minBox2")
    // println("Circuits: $circuit1 -> $circuit2")

    val nextClosestTuples = curClosestTuples.slice(IntRange(1, curClosestTuples.count() - 1))
    val nextCircuitMap =  if (curCircuitMap.containsKey(minBox1) && curCircuitMap.containsKey(minBox2)) {
        curCircuitMap.merge(setOf(circuit1!!, circuit2!!))
    } else {
        val circuitId = circuit1 ?: circuit2
        // println("Circuit ID is $circuitId")
        curCircuitMap + CircuitMap.of(minBox1 to circuitId, minBox2 to circuitId)
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
// closestTuples.forEach(::println)

val circuitCountMap = findCircuits(closestTuples.take(1000), { it.closestTuples.isEmpty() }, { it.circuitMap })
    .values.groupBy { it }
    .mapValues { (_, valueList) -> valueList.count().toLong() }

val sizesMultiplied = circuitCountMap.values.sortedDescending().take(3).reduce { a, b -> a * b }
println(sizesMultiplied)
