package cz.sg.kotlinexamples

fun main() {


    val o = MyObject()
    try {

        method1(o)


    } catch (e: Exception) {
        println(e.message)
    }


}

fun method1(myObject: MyObject) {
    println("method1")
    throw Exception("Exception")
}

fun method2(myObject: MyObject) {


}


class MyObject


