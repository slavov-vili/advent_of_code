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

// FIXME: something somewhere is broken
// TODO: maybe sort closest map before passing to improve performance?
fun findCircuits(curClosestTuples: List<ClosestTuple>, curCircuitMap: CircuitMap, i: Long, n: Long = Long.MAX_VALUE): CircuitMap {
    // val circuits = curCircuitMap.values.toSet().count()
    // println("\nFound $circuits circuits")
    if ( i == n - 1 || curClosestTuples.isEmpty()) {
        // FIXME: add remaining boxes to their own circuits?
        return curCircuitMap
    }

    val (minBox1, minBox2) = curClosestTuples[0]

    val circuit1 = curCircuitMap.getOrDefault(minBox1, null)
    val circuit2 = curCircuitMap.getOrDefault(minBox2, i)
    val circuitId = circuit1 ?: circuit2
    println("\nMin pair: $minBox1 -> $minBox2")
    println("Circuits: $circuit1 -> $circuit2")

    val nextClosestTuples = curClosestTuples.slice(IntRange(1, curClosestTuples.count() - 1))
    // FIXME: if they are both in - what happens?
    if (curCircuitMap.containsKey(minBox1) && curCircuitMap.containsKey(minBox2)) {
        return findCircuits(nextClosestTuples, curCircuitMap, i, n)
    }
    println("Circuit ID is $circuitId")

    val nextCircuitMap = curCircuitMap + mapOf(minBox1 to circuitId, minBox2 to circuitId)
    return findCircuits(nextClosestTuples, nextCircuitMap, i + 1, n)
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
closestTuples.forEach(::println)

val circuitMap = findCircuits(closestTuples, mapOf(), 0, 10)

val circuitCountMap: Map<Long, Long> = circuitMap.values.groupBy { it }.mapValues { (_, valueList) -> valueList.count().toLong() }
println(circuitCountMap)

