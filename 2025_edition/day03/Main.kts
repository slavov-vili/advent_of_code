package day03

import java.io.File

fun String.findMaxJolts(size: Int): Long {
    val indices = IntRange(0, size - 1).fold(mutableListOf<Int>()) { indices, i ->
        val firstIndex = (indices.lastOrNull() ?: -1) + 1
        val lastIndex = this.count() - size + i
        val max_i = IntRange(firstIndex, lastIndex).maxBy { this[it] }
        indices.add(max_i)
        indices
    }
    return indices.map { this[it] } .joinToString("").toLong()
}

val input = File("input.txt").readLines().filterNot(String::isEmpty)

val maximumsA = input.map { it.findMaxJolts(2) }
println("Maximum sum A: ${maximumsA.sum()}")

val maximumsB = input.map { it.findMaxJolts(12) }
println("Maximum sum B: ${maximumsB.sum()}")
