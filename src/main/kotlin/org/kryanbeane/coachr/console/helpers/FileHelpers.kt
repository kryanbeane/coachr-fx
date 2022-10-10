package org.kryanbeane.coachr.console.helpers

import mu.KotlinLogging
import java.io.*

val logger = KotlinLogging.logger {}

fun write( fileName: String, data: String) {

    val file = File(fileName)
    try {
        val outputStreamWriter = OutputStreamWriter(FileOutputStream(file))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        logger.error { "Cannot read file: $e" }
    }
}

fun read(fileName: String): String {
    val file = File(fileName)
    var str = ""
    try {
        val inputStreamReader = InputStreamReader(FileInputStream(file))
        val bufferedReader = BufferedReader(inputStreamReader)
        val partialStr = StringBuilder()
        var done = false
        while (!done) {
            val line = bufferedReader.readLine()
            done = (line == null);
            if (line != null) partialStr.append(line);
        }
        inputStreamReader.close()
        str = partialStr.toString()
    } catch (e: FileNotFoundException) {
        logger.error { "Cannot Find file: $e" }
    } catch (e: IOException) {
        logger.error { "Cannot Read file: $e" }
    }
    return str
}

fun exists(fileName: String): Boolean {
    val file = File(fileName)
    return file.exists()
}