package day05

import java.io.File

fun String.toRange(): LongRange {
    val (start, end) = this.split("-")
    return LongRange(start.toLong(), end.toLong())
}

val input = File("input.txt").readLines().filterNot(String::isEmpty)

val freshRanges = input.filter { line -> line.contains("-")}.map(String::toRange)
val available = input.filterNot { line -> line.contains("-") }.map(String::toLong)

val fresh = available.filter { x -> freshRanges.any { range -> x in range } }
println("Fresh ingredient count: ${fresh.count()}")
