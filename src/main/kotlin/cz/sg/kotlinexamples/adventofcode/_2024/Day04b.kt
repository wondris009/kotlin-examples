package cz.sg.kotlinexamples.adventofcode._2024

import java.nio.file.Files
import java.nio.file.Paths

private const val XMAS_WORD = "XMAS"

fun main() {
    val lines = Files
        .readAllLines(Paths.get("src/main/resources/2024/day04/input.txt"))

    var totalCount = 0


    val lineOccurrence = lines.sumOf { countXmasWordOccurrences(it) }
    totalCount += lineOccurrence

    val lineReversedOccurrence = lines.sumOf { countXmasWordOccurrences(it.reversed()) }
    totalCount += lineReversedOccurrence

    val matrix = List(lines.size) { i -> lines[i].toList() }
    val transposedMatrix = transposeMatrix(matrix)

    val transposedLines = mutableListOf<String>()
    transposedMatrix.forEach { transposedLines.add(it.joinToString("")) }

    val columnOccurrence = transposedLines.sumOf { countXmasWordOccurrences(it) }
    totalCount += columnOccurrence

    val colReversedOccurrence = transposedLines.sumOf { countXmasWordOccurrences(it.reversed()) }
    totalCount += colReversedOccurrence

    val n = matrix.size
    val mainDiagonals = mutableMapOf<Int, MutableList<Char>>()
    for (r in 0 until n) {
        for (c in 0 until n) {
            val diff = r - c
            mainDiagonals.computeIfAbsent(diff) { mutableListOf() }.add(matrix[r][c])
        }
    }

    val antiDiagonals = mutableMapOf<Int, MutableList<Char>>()
    for (r in 0 until n) {
        for (c in 0 until n) {
            val sum = r + c
            antiDiagonals.computeIfAbsent(sum) { mutableListOf() }.add(matrix[r][c])
        }
    }

    var mainDiagonalsCount = 0
    mainDiagonals.forEach { (key, value) ->
        val string = value.joinToString("")
        mainDiagonalsCount += countXmasWordOccurrences(string)
        mainDiagonalsCount += countXmasWordOccurrences(string.reversed())
    }

    var mainDiagonalsCount2 = 0
    antiDiagonals.forEach { (key, value) ->
        val string = value.joinToString("")
        mainDiagonalsCount2 += countXmasWordOccurrences(string)
        mainDiagonalsCount2 += countXmasWordOccurrences(string.reversed())
    }

    totalCount += mainDiagonalsCount
    totalCount += mainDiagonalsCount2

    println(totalCount)
}

private fun countXmasWordOccurrences(line: String) = XMAS_WORD.toRegex().findAll(line).count()

private fun printMatrix(matrix: List<List<Char>>) {
    for (row in matrix.indices) {
        for (col in matrix[row].indices) {
            val char = matrix[row][col]
            // Here, 'char' is the character at coordinates (row, col)
            println("Character at ($row, $col): $char")
        }
    }
}

private fun transposeMatrix(matrix: List<List<Char>>): List<List<Char>> {
    val rows = matrix.size
    val cols = matrix[0].size
    val transposed = List(cols) { MutableList(rows) { 'o' } }
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            transposed[j][i] = matrix[i][j]
        }
    }
    return transposed
}