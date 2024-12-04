package cz.sg.kotlinexamples.adventofcode._2024

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val data = Files
        .readAllLines(Paths.get("src/main/resources/2024/day01/input.txt"))
        .map {
            val columns = it.split("   ")
            Pair(columns[0].toInt(), columns[1].toInt())
        }

    val leftList = data.map { it.first }.sorted()
    val rightList = data.map { it.second }.sorted()

    val result = mutableListOf<Int>()
    leftList.forEachIndexed { index, leftValue ->
        result.add(Math.abs(leftValue - rightList[index]))
    }
    println(result.sum())
}