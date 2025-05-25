package cz.sg.kotlinexamples

import java.io.File
import java.io.RandomAccessFile

fun main() {
    val filePath = "/Users/vondracek/tmp/addresses/"
    val fileName = "dmp_building_lowell_202504.txt"

//    buildLineIndex("$filePath/$fileName", "$filePath/idx/largefile.idx")

    measureDuration {
//        280MB, 12% CPU
//        00:06:38.641
        for (i in 22_000_001..23_000_000 step 10_000) {
            println("Processing $i")
            measureDuration {
                readLinesFromIndexedFile("$filePath/$fileName", "$filePath/idx/largefile.idx", i, i + 9_999)
            }

        }
    }
}

fun buildLineIndex(filePath: String, indexPath: String) {
    RandomAccessFile(filePath, "r").use { raf ->
        File(indexPath).printWriter().use { indexWriter ->
            var pos = raf.filePointer
            indexWriter.println(pos) // First line offset
            while (raf.readLine() != null) {
                pos = raf.filePointer
                indexWriter.println(pos)
            }
        }
    }
}

fun readLinesFromIndexedFile(filePath: String, indexPath: String, fromLine: Int, toLine: Int): List<String> {
    val offsets = File(indexPath).useLines { lines ->
        lines.drop(fromLine - 1).take(toLine - fromLine + 2).map { it.toLong() }.toList()
    }

    val result = mutableListOf<String>()
    RandomAccessFile(filePath, "r").use { raf ->
        for (i in 0 until offsets.size - 1) {
            raf.seek(offsets[i])
            val lineBytes = ByteArray((offsets[i + 1] - offsets[i]).toInt())
            raf.readFully(lineBytes)
            result.add(String(lineBytes).trimEnd('\n', '\r'))
        }
    }
    return result
}




