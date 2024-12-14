package cz.sg.kotlinexamples.adventofcode._2024

import java.nio.file.Files
import java.nio.file.Paths

object LineReader {
    fun readLines(day: String) = Files.readAllLines(Paths.get("src/main/resources/2024/day$day/input.txt"))
}