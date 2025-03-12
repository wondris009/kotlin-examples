package cz.sg.kotlinexamples


fun main() {

    try {
        method1()
    } catch (e: Exception) {
        println("error1")
    }

    try {
        method2()
    } catch (e: Exception) {
        println("error2")
    }

    // -----

    try {
        method1WithProblem()
        try {
            method2()
        } catch (e: Exception) {
            println("error2")
        }
    } catch (e: Exception) {
        println("error1")
    }
}

private fun method1() = println("method1")
private fun method1WithProblem() {
    println("method1")
    throw Exception("exception1")
}
private fun method2() = println("method2")
private fun method2WithProblem() {
    println("method2")
    throw Exception("exception2")
}