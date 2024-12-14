package cz.sg.kotlinexamples.adventofcode._2024

fun main() {

    val lines = LineReader.readLines("03")
    val regexp = "mul\\([0-9]{1,3},[0-9]{1,3}\\)"

    val multiplyOperations = mutableListOf<String>()
    lines.forEach { line ->

        val matches = regexp.toRegex().findAll(line)
        multiplyOperations.addAll(matches.map { it.value })
    }

    val multiplications = mutableListOf<Int>()
    multiplyOperations.forEach { operationString ->
        multiplications.add(Day03Util.getMultiplication(operationString))
    }
    println(multiplications.sum())
}