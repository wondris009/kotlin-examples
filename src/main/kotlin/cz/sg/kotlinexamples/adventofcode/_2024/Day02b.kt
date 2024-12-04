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
        val isMainListOk = checkList(list)

        var isSubListsOk = false
        if(!isMainListOk) {
            val sublistResult = mutableListOf<Boolean>()
            val subLists = createSubListWithOneRemovedElement(list)
            subLists.forEach { subList ->
                val isSubListOK = checkList(subList)
                sublistResult.add(isSubListOK)
            }
            isSubListsOk = sublistResult.any { it }
        }

        if(isMainListOk || isSubListsOk) {
            result++
        }
    }
    println(result)
}


fun checkList(list: List<Int>): Boolean {
    val signList = mutableListOf<String>()
    list.zipWithNext().forEach { (a, b) ->
        if (a - b < 0) signList.add("-") else signList.add("+")
    }
    val differenceBetweenTwoElementsIsBetweenOneAndThree = list.zipWithNext().all { (a, b) ->
        abs(a - b) in listOf(1, 2, 3)
    }
    return differenceBetweenTwoElementsIsBetweenOneAndThree && signList.all { it == "-" } || differenceBetweenTwoElementsIsBetweenOneAndThree && signList.all { it == "+" }
}

fun createSubListWithOneRemovedElement(numbers: List<Int>): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    IntRange(0, numbers.size - 1).forEach { i ->
        val listCopy = mutableListOf<Int>().apply { addAll(numbers) }
        listCopy.removeAt(i)
        result.add(listCopy)
    }
    return result
}