package cz.sg.kotlinexamples

import java.io.FileInputStream
import java.util.zip.ZipInputStream

fun main(args: Array<String>) {
    tryToUnzipFile("dmp_building_lowell_202410.zip")
    tryToUnzipFile("dmp_building_lowell_202409.zip")
}

fun tryToUnzipFile(fileName: String) {
    try {
        val zipStream = ZipInputStream(FileInputStream("/Users/vondracek/tmp/ANS2/zip/$fileName"))
        val entry = zipStream.nextEntry
        if (entry != null) {
            println("File $fileName was unzipped correctly")
        } else {
            zipStream.closeEntry()
            zipStream.close()
            println("Unable to unzip file $fileName")
        }

    } catch (e: Exception) {
        println("exception")
    }
}