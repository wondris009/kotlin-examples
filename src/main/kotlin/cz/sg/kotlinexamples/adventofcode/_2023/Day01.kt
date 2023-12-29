package cz.sg.kotlinexamples.adventofcode._2023

import java.nio.file.Files
import java.nio.file.Paths

fun main() {

    val lines = Files.readAllLines(Paths.get("/Users/vondracek/tmp/advent-of-code/day-01/input.txt"))

    val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "zero" to 0
    )

    val result = mutableListOf<Int>()

    lines.forEach { input ->

        val indexesAndValues = mutableListOf<IndexValue>()

        numbers.keys.forEach { number ->

            input.forEachIndexed { index, char ->
                if (char.isDigit()) {
                    indexesAndValues.add(IndexValue(index, char.digitToInt()))
                }
            }

            val indices = Regex(number).findAll(input)
                .map { it.range.first }
                .toList()

            indices.forEach { indexOf ->
                if (indexOf != -1) {
                    val sub = input.substring(indexOf, number.length + indexOf)

                    val indexValue = IndexValue(indexOf, numbers[sub]!!)
                    if (!indexesAndValues.contains(indexValue)) {
                        indexesAndValues.add(indexValue)
                    }
                }
            }
        }

        val first = indexesAndValues.minByOrNull { it.index }!!.value
        val last = indexesAndValues.maxByOrNull { it.index }!!.value

        val wholeNumber = "$first$last".toInt()

        result.add(wholeNumber)

        indexesAndValues.clear()
    }

    val finaResult = result.sum()

    println(finaResult)
}

data class IndexValue(var index: Int, val value: Int)