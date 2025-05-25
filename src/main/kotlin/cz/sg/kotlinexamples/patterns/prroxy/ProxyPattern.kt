package cz.sg.kotlinexamples.patterns.prroxy

interface ProxyInterface {
    fun doSomething()
}

class RealObject: ProxyInterface {
    override fun doSomething() {
        println("I am real object and i am doing something")
    }
}

class ProxyObject(private val realObject: RealObject): ProxyInterface {
    override fun doSomething() {
        println("Proxy is doing something before real object")
        realObject.doSomething()
        println("Proxy is doing something after real object")
    }
}

fun main() {
    val realObject = RealObject()
    val proxyObject = ProxyObject(realObject)
    proxyObject.doSomething()
}
