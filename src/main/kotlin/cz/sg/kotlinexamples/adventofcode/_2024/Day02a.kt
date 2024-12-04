package cz.sg.kotlinexamples.adventofcode._2024

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val numbersList = Files
        .readAllLines(Paths.get("src/main/resources/2024/day02/input.txt"))
        .filter { it.isNotBlank() }
        .map {
            val columns = it.split(" ")
            listOf(columns.map { numbers -> numbers.toInt() })
        }.flatten()

    var result = 0
    numbersList.forEach { list ->
        val signList = mutableListOf<String>()
        list.zipWithNext().forEach { (a, b) ->
            if (a - b < 0) signList.add("-") else signList.add("+")
        }
        val differenceBetweenTwoElementsIsBetweenOneAndThree = list.zipWithNext().all { (a, b) ->
            abs(a - b) in listOf(1, 2, 3)
        }
        if (differenceBetweenTwoElementsIsBetweenOneAndThree && signList.all { it == "-" } || differenceBetweenTwoElementsIsBetweenOneAndThree && signList.all { it == "+" }) {
            println("true: $list")
            result++
        } else {
            println("false: $list")
        }
    }
    println(result)
}