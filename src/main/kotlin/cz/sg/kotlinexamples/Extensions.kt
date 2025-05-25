package cz.sg.kotlinexamples

import kotlin.system.measureTimeMillis

fun measureDuration(fn: () -> Unit) {
    measureTimeMillis { fn() }.formatDuration()
}

fun Long.formatDuration() {
    val hours = this / (1000 * 60 * 60)
    val minutes = (this / (1000 * 60)) % 60
    val seconds = (this / 1000) % 60
    val milliseconds = this % 1000

    println("%02d:%02d:%02d.%03d".format(hours, minutes, seconds, milliseconds))
}