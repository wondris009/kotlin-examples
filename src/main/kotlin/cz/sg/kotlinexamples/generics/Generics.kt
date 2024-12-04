package cz.sg.kotlinexamples.generics

class Box<T>(var t: T) {
    var value = t
}

fun main() {
    val box = Box(1)
    val box2 = Box("something")

}

