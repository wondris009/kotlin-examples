package cz.sg.kotlinexamples.adventofcode._2024

object Day03Util {
    fun getMultiplication(operationString: String): Int {
        val split = operationString.split(",")

        val op1 = split[0].replace("mul(", "").toInt()
        val op2 = split[1].replace(")", "").toInt()

        return op1.times(op2)
    }
}