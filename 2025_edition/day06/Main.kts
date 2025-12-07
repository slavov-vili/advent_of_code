package day06

import java.io.File

fun String.parse(separatorIndices: List<Int>): List<String> {
    return (listOf(-1) + separatorIndices + listOf(this.length)).zipWithNext()
        .map { (start, end) -> this.substring(start + 1, end) }
}

fun List<Long>.fold(str: String): List<Long> {
    val trimmed = str.trim()
    return when(trimmed) {
        "+" -> listOf(this.sum())
        "*" -> listOf(this.reduce { a, b -> a * b })
        else -> this + trimmed.toLong()
    }
}

fun List<String>.foldNumbers(): List<Long> {
    return this.fold(listOf<Long>(), List<Long>::fold)
}

fun List<String>.convertToPodDigits(): List<Long> {
    val length = this.maxOf(String::length)

    return this.fold(listOf<String>()) { acc, next ->
        IntRange(0, length - 1).map {
            val first = acc.getOrNull(it) ?: ""
            val second = if (next.substring(it, it + 1) == " ") "" else next.substring(it, it + 1)
            first + second
        }
    }.map(String::toLong)
}

fun List<String>.foldStrings(): List<String> {
    return this.fold(listOf<String>()) { strings, str ->
        val trimmed = str.trim()
         when(trimmed) {
            "+", "*" -> strings.convertToPodDigits().fold(trimmed).map(Long::toString)
            else -> strings + str
        }
    }
}

fun <T> List<List<String>>.calcPodSum(folder: (List<String>) -> List<T>, longParser: (T) -> Long): Long {
    return this.flatMap(folder).map(longParser).sum()
}



val lines: List<String> = File("input.txt")
    .readLines().filterNot(String::isEmpty)
val separatorIndices = IntRange(0, lines.first().length - 1).filter { i ->
    lines.all { line -> line[i] == ' '}
}

val empties = IntRange(0, separatorIndices.count()).map { it -> listOf<String>() }
val columns: List<List<String>> = lines.fold(empties) { col, line ->
    col.zip(line.parse(separatorIndices)) { col, line -> col + line }
}

val sumA = columns.calcPodSum(List<String>::foldNumbers) { it }
println("Sum A: $sumA")

val sumB = columns.calcPodSum(List<String>::foldStrings) { it.toLong() }
println("Sum B: $sumB")
