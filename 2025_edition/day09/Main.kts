package day09

import java.io.File
import kotlin.math.abs

data class RedTile(val line: Long, val col: Long) {

    companion object {
        fun fromLine(line: String): RedTile {
            val split = line.split(",")
            return RedTile(split[0].toLong(), split[1].toLong())
        }
    }

    fun calcArea(other: RedTile): Long {
        return (abs(this.line - other.line) + 1) * (abs(this.col - other.col) + 1)
    }
}

val input = File("input.txt").readLines().filterNot(String::isEmpty)
    .map(RedTile::fromLine)

val maxArea = IntRange(0, input.count() - 2).flatMap { i ->
    IntRange(1, input.count() - 1).map { j ->
        Pair(input[i], input[j])
    }
}.maxOf { it.first.calcArea(it.second) }
println("Max area: $maxArea")
