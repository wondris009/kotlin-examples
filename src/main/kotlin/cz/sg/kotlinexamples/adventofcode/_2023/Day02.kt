package cz.sg.kotlinexamples.adventofcode._2023

import java.nio.file.Files
import java.nio.file.Paths

fun main() {

    /*
    Game 1: 1 red, 5 blue, 1 green; 16 blue, 3 red; 6 blue, 5 red; 4 red, 7 blue, 1 green
    Game 2: 4 blue; 4 red, 3 blue, 1 green; 4 red, 9 blue, 2 green; 5 blue, 7 green, 4 red
    Game 3: 10 blue; 7 blue, 1 green; 19 blue, 1 green, 9 red
    Game 4: 2 green; 14 blue, 14 red, 4 green; 12 red, 11 green, 13 blue; 5 green, 9 red, 4 blue; 9 red, 7 green, 12 blue; 2 green, 3 blue, 8 red
    Game 5: 3 blue, 4 red; 12 red, 2 green, 15 blue; 1 red, 10 blue, 1 green
    Game 6: 1 blue, 7 red; 3 green, 5 red, 1 blue; 1 green, 7 red; 6 red, 1 blue, 4 green; 1 green, 8 red, 1 blue; 2 green, 4 red, 1 blue
    Game 7: 11 green, 10 blue, 2 red; 1 green, 12 blue, 2 red; 9 green, 14 blue; 1 red, 19 blue, 15 green
    Game 8: 4 green, 2 red, 14 blue; 9 green, 1 red, 15 blue; 2 green, 9 red, 8 blue; 11 green, 7 red, 8 blue; 9 red, 7 green, 6 blue
    Game 9: 4 blue, 1 green, 2 red; 1 blue, 3 red; 1 red, 3 blue, 3 green
    Game 10: 4 red, 3 green, 6 blue; 2 green, 15 blue, 6 red; 3 green, 2 blue; 2 red, 1 green; 11 blue, 7 red, 4 green; 2 blue, 2 red, 4 green
     */

    val bag = readBag()

    printBagTotals(bag)

    val part1Result = getPart1(bag)
    println("Part1 result: $part1Result")

    val part2Result = getPart2(bag)
    println("Part2 result: $part2Result")

    println("\n")
}

private fun printBagTotals(bag: Bag) {
    println("Bag totals")
    bag.games.forEach {
        println("${it.id}: red: ${it.getCount(Color.RED)}, green: ${it.getCount(Color.GREEN)}, blue: ${it.getCount(Color.BLUE)}")
    }
    println("")
}

private fun readBag(): Bag {
    val lines = Files.readAllLines(Paths.get("/Users/vondracek/tmp/advent-of-code/day-02/input.txt"))

    val bag = Bag()

    lines.map { line ->
        val idAndRest = line.split(":")
        val gameId = idAndRest[0].split(" ")[1].toInt()
        val gamesStringList = idAndRest[1].split(";")

        val game = Game(gameId)

        gamesStringList.forEach { gamesString ->
            val cubesString = gamesString.split(",")
            cubesString.forEach { kubes ->
                val cubeString = kubes.split(" ").filter { it.trim().isNotEmpty() }

                val count = cubeString[0].toInt()
                val color = Color.valueOf(cubeString[1].uppercase())

                game.addCube(Cube(color, count))
            }
        }
        bag.addGame(game)
    }
    return bag
}

fun getPart1(bag: Bag): Int {
    val redCountMax = 12
    val greenCountMax = 13
    val blueCountMax = 14

    return bag.games
        .filter {
            it.getMaxCountByColor(Color.RED) <= redCountMax
                    && it.getMaxCountByColor(Color.GREEN) <= greenCountMax
                    && it.getMaxCountByColor(Color.BLUE) <= blueCountMax
        }.sumOf { it.id }
}

fun getPart2(bag: Bag): Int {

    var sum = 0

    bag.games.forEach {
        val redCubesCounts = it.cubes.filter { g ->
            g.color == Color.RED
        }.maxOf { g -> g.count }
        val greenCubesCounts = it.cubes.filter { g ->
            g.color == Color.GREEN
        }.maxOf { g -> g.count }
        val blueCubesCounts = it.cubes.filter { g ->
            g.color == Color.BLUE
        }.maxOf { g -> g.count }
        val multiple = redCubesCounts.times(greenCubesCounts).times(blueCubesCounts)

        sum += multiple
    }
    return sum
}

class Bag(
    val games: MutableList<Game> = mutableListOf()
) {
    fun addGame(game: Game) = games.add(game)
}

data class Game(
    val id: Int,
    val cubes: MutableList<Cube> = mutableListOf()
) {
    fun addCube(c: Cube) = cubes.add(c)

    fun getCount(c: Color) = cubes.filter { it.color == c }.sumOf { it.count }

    fun getMaxCountByColor(c: Color): Int = cubes.filter { it.color == c }.map { it.count }.max()
}


data class Cube(
    val color: Color,
    val count: Int
)

enum class Color {
    RED,
    GREEN,
    BLUE;
}