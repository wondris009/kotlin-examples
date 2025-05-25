package cz.sg.kotlinexamples.adventofcode._2024

fun main() {
    // -----------------------------------------------------
    // 1. Parse input
    // -----------------------------------------------------
    val input = LineReader.readLines("06")

    val numRows = input.size
    val numCols = input[0].length

    // Directions in order: up, right, down, left
    // We'll cycle them with an index: (index + 1) % 4 to turn right
    val directions = listOf(
        Pair(-1, 0),   // Up
        Pair(0, 1),    // Right
        Pair(1, 0),    // Down
        Pair(0, -1)    // Left
    )

    // Find guard start position and facing
    var guardStartRow = -1
    var guardStartCol = -1
    var guardStartDirIndex = 0 // up=0, right=1, down=2, left=3

    loop@ for (r in input.indices) {
        for (c in input[r].indices) {
            when (input[r][c]) {
                '^' -> {
                    guardStartRow = r
                    guardStartCol = c
                    guardStartDirIndex = 0
                    break@loop
                }
                '>' -> {
                    guardStartRow = r
                    guardStartCol = c
                    guardStartDirIndex = 1
                    break@loop
                }
                'v' -> {
                    guardStartRow = r
                    guardStartCol = c
                    guardStartDirIndex = 2
                    break@loop
                }
                '<' -> {
                    guardStartRow = r
                    guardStartCol = c
                    guardStartDirIndex = 3
                    break@loop
                }
            }
        }
    }

    // -----------------------------------------------------
    // 2. Function to simulate guard's route
    //    Returns true if a loop is detected, false if guard exits
    // -----------------------------------------------------
    fun doesGuardLoopWithObstacle(newObsRow: Int, newObsCol: Int): Boolean {
        var row = guardStartRow
        var col = guardStartCol
        var dirIndex = guardStartDirIndex

        // We’ll store visited states to detect loops:
        // (row, col, dirIndex)
        val seenStates = mutableSetOf<Triple<Int, Int, Int>>()

        while (true) {
            // If we've been in exactly this (r,c,direction) before, it's a loop
            val state = Triple(row, col, dirIndex)
            if (state in seenStates) {
                return true
            }
            seenStates.add(state)

            // Determine the cell in front of the guard
            val (dr, dc) = directions[dirIndex]
            val frontR = row + dr
            val frontC = col + dc

            // Check out-of-bounds?
            if (frontR !in 0 until numRows || frontC !in 0 until numCols) {
                // Guard would leave map
                return false
            }

            // Check if there's an obstacle in front (either original # or new obstacle)
            // Use input[frontR][frontC] or compare to newObsRow/newObsCol
            val isOriginalObstacle = (input[frontR][frontC] == '#')
            val isNewObstacle = (frontR == newObsRow && frontC == newObsCol)
            if (isOriginalObstacle || isNewObstacle) {
                // Turn right
                dirIndex = (dirIndex + 1) % 4
            } else {
                // Move forward
                row = frontR
                col = frontC
            }
        }
    }

    // -----------------------------------------------------
    // 3. Try placing one new obstacle in each possible cell
    //    EXCEPT the guard's starting cell or existing obstacles
    // -----------------------------------------------------
    var loopCount = 0
    for (r in 0 until numRows) {
        for (c in 0 until numCols) {
            // Skip if it's the guard's start
            if (r == guardStartRow && c == guardStartCol) continue

            // Skip if it is already an obstacle
            if (input[r][c] == '#') continue

            // Test placing an obstacle here
            if (doesGuardLoopWithObstacle(r, c)) {
                loopCount++
            }
        }
    }

    // -----------------------------------------------------
    // 4. Output the result
    // -----------------------------------------------------
    println("Number of positions that cause the guard to loop: $loopCount")
}
