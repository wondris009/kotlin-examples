package cz.sg.kotlinexamples.adventofcode._2023

import java.nio.file.Files
import java.nio.file.Paths

fun main() {

    val engine = readEngine()
    engine.fillData()

    val filteredNumbers = engine.filterNumbersWithSymbols()
    val part1 = filteredNumbers.map {
        it.numbers.joinToString(separator = "").toInt()
    }

    println(part1.sum())
}

fun readEngine(): Engine {

    val lines = Files.readAllLines(Paths.get("src/main/resources/2023/day03/input.txt"))

    val engine = Array(lines.size) { CharArray(10) }
    lines.forEachIndexed { index, line ->
        engine[index] = line.toCharArray()
    }

    return Engine(engine)
}

class Engine(
    private val characters: Array<CharArray>,
) {

    private val numbers = mutableListOf<NumbersAndIndex>()

    fun fillData() {
        characters.forEachIndexed { lineIndex, lineCharacters ->
            val numberList = mutableListOf<Int>()
            lineCharacters.forEachIndexed { index, char ->
                if (char.isDigit()) {
                    numberList.add(char.digitToInt())
                } else {
                    if (numberList.isNotEmpty()) {
                        val numbersAndIndex = NumbersAndIndex(numberList.toList(), lineIndex, index - numberList.size)
                        numbers.add(numbersAndIndex)
                        numberList.clear()
                    }
                }
            }
        }
    }

    fun filterNumbersWithSymbols(): List<NumbersAndIndex> {

        val result = mutableListOf<NumbersAndIndex>()

        numbers.forEach { numbersAndIndex ->
            val symbolIsNear = mutableListOf<Boolean>()
            numbersAndIndex.numbers.forEachIndexed { index, number ->
                val lineIndex = numbersAndIndex.lineIndex
                val rowIndex = numbersAndIndex.rowIndex + index
                if (characters.symbolIsNear(lineIndex, rowIndex)) {
                    symbolIsNear.add(true)
                } else {
                    symbolIsNear.add(false)
                }
            }

            val numbersHasSymbolNear = symbolIsNear.any { it }
            if (numbersHasSymbolNear) {
                result.add(numbersAndIndex)
            }
        }
        return result
    }
}

class NumbersAndIndex(
    val numbers: List<Int>,
    val lineIndex: Int,
    val rowIndex: Int,
)

fun Array<CharArray>.isSymbol(lineIndex: Int, rowIndex: Int): Boolean {
    return try {
        !(this[lineIndex][rowIndex].isDigit() || this[lineIndex][rowIndex] == '.')
    } catch (e: ArrayIndexOutOfBoundsException) {
        return false
    }
}

fun Array<CharArray>.symbolIsNear(lineIndex: Int, rowIndex: Int): Boolean {
    return this.isSymbol(lineIndex - 1, rowIndex - 1)
            || this.isSymbol(lineIndex - 1, rowIndex)
            || this.isSymbol(lineIndex - 1, rowIndex + 1)
            || this.isSymbol(lineIndex, rowIndex - 1)
            || this.isSymbol(lineIndex, rowIndex + 1)
            || this.isSymbol(lineIndex + 1, rowIndex - 1)
            || this.isSymbol(lineIndex + 1, rowIndex)
            || this.isSymbol(lineIndex + 1, rowIndex + 1)
}