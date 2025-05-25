package cz.sg.kotlinexamples.sg

import cz.sg.kotlinexamples.sg.Operation.*
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.random.Random

fun main() {

    val examples = mutableListOf<String>()

    val operations = listOf(PLUS, MINUS)

    val numberOfExamples = 100
    val maximumOperandValue = 25

    var example = ""
    var index = 1
    repeat(numberOfExamples) {
        val random1 = Random.nextInt(1, maximumOperandValue + 1)
        val random2 = Random.nextInt(1, maximumOperandValue + 1)
        val operation = operations[Random.nextInt(0, 2)]

        // Extract ordered operands if necessary.
        val (a, b) = getOrderedOperands(random1, random2, operation)

        example += printExample(a, b, operation)
        if(index % 3 == 0) {
            examples.add(example)
            example = ""
        }
        index++
    }

    Files.write(Paths.get("/Users/vondracek/tmp/examples.txt"), examples)
}

private fun getOrderedOperands(a: Int, b: Int, operation: Operation): Pair<Int, Int> {
    return if (operation.sign == "-" && a < b) Pair(b, a) else Pair(a, b)
}


//private fun printExample(a: Int, b: Int, operation: Operation) = "$a ${operation.sign} $b =          "
private fun printExample(a: Int, b: Int, operation: Operation): String {
    // The format string pads each operand to at least 2 digits.
    // The equals sign will always be in the same column.
    return String.format("%2d %s %2d =          ", a, operation.sign, b)
}

enum class Operation(val sign: String) {
    PLUS("+"), MINUS("-")
}

