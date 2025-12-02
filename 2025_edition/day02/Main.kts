package day02

import java.io.File

fun String.toRange(): LongRange {
    val (start, end) = this.split("-").map(String::toLong)
    return start..end
}

fun Long.isInvalidID(size: Int): Boolean {
    val str = this.toString()
    if (str.length % size != 0) {
        return false
    }

    val substringRanges = (0..(str.length / size - 1)).map { i ->
        (size * i)..(size * (i + 1) - 1)
    }

    return substringRanges.map(str::substring).zipWithNext().all { pair -> pair.first == pair.second }
}

fun Long.isInvalidForA(): Boolean {
    return this.isInvalidID(2)
}

fun Long.isInvalidForB(): Boolean {
    val str = this.toString()
    return (1..str.length / 2).any { this.isInvalidID(it) }
}

val ranges = File("input.txt").readLines().first().split(",").map(String::toRange)

val sumA = ranges.sumOf { it.filter(Long::isInvalidForA).sum() }
println("Sum A: $sumA")

val sumB = ranges.sumOf { it.filter(Long::isInvalidForB).sum() }
println("Sum B: $sumB")
