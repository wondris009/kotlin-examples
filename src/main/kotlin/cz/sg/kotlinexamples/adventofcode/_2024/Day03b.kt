package cz.sg.kotlinexamples.adventofcode._2024

import java.io.File

fun main() {

    val line = File("./src/main/resources/2024/day03/input.txt").readText()

    val multiplyOperationRegex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)"
    val doOperatorRegex = "do\\(\\)"
    val dontOperatorRegex = "don't\\(\\)"

    val multiplyOperations = getMatches(line, multiplyOperationRegex, OperatorType.MUL)
    val doOperations = getMatches(line, doOperatorRegex, OperatorType.DO)
    val dontOperations = getMatches(line, dontOperatorRegex, OperatorType.DONT)

    val allTogether = mutableListOf<Match>()

    allTogether.addAll(multiplyOperations)
    allTogether.addAll(doOperations)
    allTogether.addAll(dontOperations)

    allTogether.sortBy { it.matchResult.range.first }

    var count = true
    allTogether.forEach { match ->
        if (match.type == OperatorType.DO) {
            count = true
        } else if (match.type == OperatorType.DONT) {
            count = false
        }

        if (count) {
            match.enabled = true
        }
    }

    allTogether.filter { it.enabled && it.type == OperatorType.MUL }.sumOf { match ->
        Day03Util.getMultiplication(match.matchResult.value)
    }.let { println(it) }
}

private fun getMatches(
    line: String,
    regex: String,
    type: OperatorType
): MutableList<Match> {
    val multiplyOperations = mutableListOf<Match>()
    val matches = regex.toRegex().findAll(line)
    multiplyOperations.addAll(matches.map { Match(it, type) })
    return multiplyOperations
}

class Match(val matchResult: MatchResult, val type: OperatorType, var enabled: Boolean = false)

enum class OperatorType {
    MUL, DO, DONT
}

