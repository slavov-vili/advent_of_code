package day08

import java.io.File
import kotlin.math.sqrt
import kotlin.math.pow

typealias Position3D = Triple<Long, Long, Long>
typealias CircuitMap = Map<Position3D, Long>

data class ClosestTuple(val box1: Position3D, val box2: Position3D, val distance: Double)

fun Position3D.calcDist(other: Position3D): Double {
    val distFirst = (this.first - other.first).toDouble()
    val distSecond = (this.second - other.second).toDouble()
    val distThird = (this.third - other.third).toDouble()
    return sqrt(distFirst.pow(2) + distSecond.pow(2) + distThird.pow(2))
}

fun parseLine(line: String): Position3D {
    val split = line.split(",").map(String::toLong)
    return Position3D(split[0], split[1], split[2])
}

fun emptyCircuitMap(): CircuitMap {
    return mapOf<Position3D, Long>()
}

fun CircuitMap.merge(circuits: Set<Long>): CircuitMap {
    val mergedCircuit = circuits.minOf { it }
    return this.mapValues { if (circuits.contains(it.value)) mergedCircuit else it.value }
}

// FIXME: runs out of heap space... too many pairs?
fun findCircuits(curClosestTuples: List<ClosestTuple>, curCircuitMap: CircuitMap, i: Long): CircuitMap {
    if ( curClosestTuples.isEmpty()) {
        // FIXME: add remaining boxes to their own circuits?
        return curCircuitMap
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
        curCircuitMap + mapOf(minBox1 to circuitId, minBox2 to circuitId)
    }

    val next_i = if (circuit1 == circuit2) i else i + 1
    return findCircuits(nextClosestTuples, nextCircuitMap, next_i)
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
    .take(1000)
// closestTuples.forEach(::println)

val circuitCountMap = findCircuits(closestTuples, mapOf(), 0)
    .values.groupBy { it }
    .mapValues { (_, valueList) -> valueList.count().toLong() }

val sizesMultiplied = circuitCountMap.values.sortedDescending().take(3).reduce { a, b -> a * b }
println(sizesMultiplied)
