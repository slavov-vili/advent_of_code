package day05

import java.io.File

fun String.toRange(): LongRange {
    val (start, end) = this.split("-")
    return LongRange(start.toLong(), end.toLong())
}

val rangeComparator = compareBy<LongRange> { it.start }.thenBy { it.endInclusive }

fun LongRange.mergeWith(other: LongRange): List<LongRange> {
    val overlap = other.start in this || this.start in other
    if (overlap) {
        return listOf(LongRange(minOf(this.start, other.start), maxOf(this.endInclusive, other.endInclusive)))
    }

    val lessThanOrEqual = rangeComparator.compare(this, other) <= 0
    val min = if (lessThanOrEqual) this else other
    val max = if (lessThanOrEqual) other else this
    return listOf(min, max)
}

fun List<LongRange>.sortAndCompress(): List<LongRange> {
    return this.sortedWith(rangeComparator)
        .fold(listOf<LongRange>()) { ranges, next ->
            val prev = if (ranges.isEmpty()) next else ranges.last()
            ranges.subList(0, maxOf(0, ranges.count()-1)) + prev.mergeWith(next)
        }
}

fun List<LongRange>.countAll(): Long {
    return this.map { it.endInclusive - it.start + 1}.sum()
}

val input = File("input.txt").readLines().filterNot(String::isEmpty)

val freshRanges = input.filter { line -> line.contains("-")}.map(String::toRange)
val available = input.filterNot { line -> line.contains("-") }.map(String::toLong)

val fresh = available.filter { x -> freshRanges.any { range -> x in range } }
println("Available fresh ingredient count: ${fresh.count()}")

val allFresh = freshRanges.sortAndCompress()
println("All fresh ingredient count: ${allFresh.countAll()}")
