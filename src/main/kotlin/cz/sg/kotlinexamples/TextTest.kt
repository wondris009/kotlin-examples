package cz.sg.kotlinexamples

import java.io.FileInputStream
import java.util.zip.ZipInputStream

fun main() {
    val filePath = "/Users/vondracek/tmp/addresses/"
    val fileName = "dmp_building_lowell_202504.txt"

//    buildLineIndex("$filePath/$fileName", "$filePath/idx/largefile.idx")

    measureDuration {
        for (i in 22_000_001..23_000_000 step 10_000) {
            println("Processing $i")
            measureDuration {
                FileInputStream("$filePath/$fileName").bufferedReader(Charsets.UTF_8).useLines { sequence ->
                    processDataFromSequence(i, sequence)
                }
            }
        }
    }
}


private fun processDataFromSequence(
    from: Int,
    chunkRows: Sequence<String>,
) {
    val lines = mutableListOf<String>()
    chunkRows.drop(from - 1).take(10_000)
        .withIndex()
        .forEach { row ->
            lines.add(row.value)
        }
}