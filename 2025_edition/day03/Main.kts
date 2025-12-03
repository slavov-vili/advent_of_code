package day03

import java.io.File

fun String.findMaxJolts(): Int {
    val max_i = IntRange(0, this.count()-2).maxBy { this[it] }
    val max_j = IntRange(max_i + 1, this.count()-1).maxBy { this[it] }
    return "${this[max_i]}${this[max_j]}".toInt()
}

val input = File("input.txt").readLines().filterNot(String::isEmpty)

val maximums = input.map(String::findMaxJolts)

println("Maximum sum A: ${maximums.sum()}")
