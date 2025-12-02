package day02

import java.io.File

fun String.toRange(): LongRange {
    val (start, end) = this.split("-").map(String::toLong)
    return LongRange(start, end)
}

fun Long.isInvalidID(size: Int): Boolean {
    val str = this.toString()
    val mid = str.length / size
    return str.substring(0, mid) == str.substring(mid, str.length)
}

val ranges = File("input.txt").readLines().first().split(",").map(String::toRange)

ranges.sumOf { it.filter { it.isInvalidID(2) }.sum() }

