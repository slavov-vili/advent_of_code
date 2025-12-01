package day01

import java.io.File
import kotlin.math.abs
import java.lang.Math.floorMod

typealias State = Pair<Int, Int>

val input = File("input.txt").readLines().filterNot(String::isEmpty)

fun rotate(state: State, instruction: String, countExtras: Boolean): State {
    val (dial_old, pw_old) = state

    val increment = instruction.replaceFirst("L", "-").replaceFirst("R", "+").toInt()
    val dial_inc = dial_old + increment

    val dial_new = floorMod(dial_inc, 100)
    var pw_new = pw_old
    if (dial_new == 0) {
        pw_new++
    }
    println("Old dial = $dial_old, Increment = $increment")
    println("New dial = $dial_new")
    if (countExtras) {
        // FIXME: 50 - 68 doesn't work :(
        val extra_clicks = abs(dial_inc) / 100
        println("Counting extra clicks: dial_inc = $dial_inc => $extra_clicks")
        pw_new += extra_clicks
    }
    println("New password = $pw_new")
    return State(dial_new, pw_new)
}

val resultA = input.fold(Pair(50, 0)) { dial, instruction -> rotate(dial, instruction, false) }
println("Password A: $resultA")

val resultB = input.fold(Pair(50, 0)) { dial, instruction -> rotate(dial, instruction, true) }
println("Password B: $resultB")
