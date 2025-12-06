package day06

import java.io.File

fun List<Long>.accumulate(str: String): List<Long> {
    return when(str) {
        "+" -> listOf(this.sum())
        "*" -> listOf(this.reduce { a, b -> a * b })
        else -> this + str.toLong()
    }
}

fun List<List<Long>>.accumulateEach(line: List<String>): List<List<Long>> {
    return this.zip(line) { acc, str -> acc.accumulate(str) }
}

val input = File("input.txt").readLines().filterNot(String::isEmpty).map { it.split("\\s+".toRegex()) }
val empties = input.first().map { listOf<Long>() }

val sums = input.fold(empties) { acc, next -> acc.accumulateEach(next) }.map(List<Long>::first).sum()
println(sums)
