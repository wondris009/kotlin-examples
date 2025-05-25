package cz.sg.kotlinexamples.adventofcode._2024

import java.nio.file.Files
import java.nio.file.Paths
fun main() {
    // ---- STEP 1: Read ordering rules ----
    val orderingRules = mutableListOf<Pair<Int, Int>>()

    // Read until a blank line or until input ends
    while (true) {
        val line = readLine() ?: break

        // Break on blank line or something without "|"
        if (line.isBlank() || !line.contains("|")) {
            break
        }

        val (left, right) = line.split("|").map { it.trim().toInt() }
        orderingRules.add(left to right)
    }

    // ---- STEP 2: Read updates ----
    val updates = mutableListOf<List<Int>>()
    while (true) {
        val line = readLine() ?: break
        if (line.isBlank()) break

        val pages = line.split(",").map { it.trim().toInt() }
        updates.add(pages)
    }

    // ---- STEP 3: Separate the updates into correct vs. incorrect ----
    val correctUpdates = mutableListOf<List<Int>>()
    val incorrectUpdates = mutableListOf<List<Int>>()

    for (update in updates) {
        if (isCorrectlyOrdered(update, orderingRules)) {
            correctUpdates.add(update)
        } else {
            incorrectUpdates.add(update)
        }
    }

    // ---- STEP 4: For the correct ones, sum up middle pages ----
    val sumOfCorrectMiddles = correctUpdates.sumOf { update ->
        update[update.size / 2]  // middle index in 0-based indexing
    }

    // ---- STEP 5: For the incorrect ones, reorder and sum up middle pages ----
    val sumOfReorderedMiddles = incorrectUpdates.sumOf { update ->
        val reordered = reorderPages(update, orderingRules)
        reordered[reordered.size / 2]
    }

    // ---- Print results ----
    println("Sum of middles (correct updates only): $sumOfCorrectMiddles")
    println("Sum of middles (reordered incorrect updates): $sumOfReorderedMiddles")
}

/**
 * Checks whether the given [update] (list of pages) respects
 * all relevant rules in [orderingRules].
 * Relevant rules = only those whose pages both appear in [update].
 */
fun isCorrectlyOrdered(update: List<Int>, orderingRules: List<Pair<Int, Int>>): Boolean {
    // Precompute the index of each page for fast lookups
    val indexMap = update.withIndex().associate { (idx, page) -> page to idx }

    // For each rule (A|B), if both A and B appear in the update, then index(A) < index(B)
    for ((beforePage, afterPage) in orderingRules) {
        val idxA = indexMap[beforePage]
        val idxB = indexMap[afterPage]

        if (idxA != null && idxB != null) {
            if (idxA >= idxB) {
                return false
            }
        }
    }

    return true
}

/**
 * Re-orders the pages in [update] according to the rules in [orderingRules].
 * Only rules that involve pages in [update] are considered.
 *
 * We can do this via a topological sort (Kahn's Algorithm):
 *   1. Build adjacency list for the pages in the update.
 *   2. Compute in-degrees of all pages.
 *   3. Repeatedly remove pages with in-degree = 0 and append them to the result.
 */
fun reorderPages(update: List<Int>, orderingRules: List<Pair<Int, Int>>): List<Int> {
    // Get the set of pages that appear in this update
    val pagesInUpdate = update.toSet()

    // Build adjacency list + in-degree map only for pages in the update
    val adjacency: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    val inDegree: MutableMap<Int, Int> = mutableMapOf()

    // Initialize adjacency list and in-degree for every page in the update
    for (p in pagesInUpdate) {
        adjacency[p] = mutableListOf()
        inDegree[p] = 0
    }

    // Fill adjacency list + compute in-degrees for relevant rules
    for ((beforePage, afterPage) in orderingRules) {
        if (beforePage in pagesInUpdate && afterPage in pagesInUpdate) {
            // We have an edge beforePage -> afterPage
            adjacency[beforePage]!!.add(afterPage)
            inDegree[afterPage] = inDegree[afterPage]!! + 1
        }
    }

    // Kahn's Algorithm for topological sort
    val queue = ArrayDeque<Int>()
    // Enqueue all nodes with in-degree 0
    for ((page, deg) in inDegree) {
        if (deg == 0) {
            queue.add(page)
        }
    }

    val sortedList = mutableListOf<Int>()

    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        sortedList.add(node)

        // For each neighbor, decrement in-degree
        for (neighbor in adjacency[node]!!) {
            inDegree[neighbor] = inDegree[neighbor]!! - 1
            if (inDegree[neighbor] == 0) {
                queue.add(neighbor)
            }
        }
    }

    // If for some reason there's a cycle (which shouldn't happen if the rules are consistent),
    // sortedList might not contain all pages. But we assume valid rules for now.
    return sortedList
}
