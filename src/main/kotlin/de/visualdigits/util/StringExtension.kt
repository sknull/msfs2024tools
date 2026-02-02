package de.visualdigits.util

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Runs the command represented by this string with the given working directory.
 */
fun String.runCommand(workingDir: File, timeOut: Pair<Long, TimeUnit> = Pair(60, TimeUnit.MINUTES)): Pair<String?, String?> {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        if (timeOut.first > 0) {
            proc.waitFor(timeOut.first, timeOut.second)
        }
        val stdout = proc.inputStream.bufferedReader().readText()
        val stderr = proc.errorStream.bufferedReader().readText()

        return Pair(stdout.ifEmpty { null }, stderr.ifEmpty { null })
    } catch(e: IOException) {
        throw IllegalStateException("Could not execute command", e)
    }
}

/**
 * Expands the variables in format ${name}
 */
fun String.expandVariables(values: Map<String, String?>): String {
    return replace("\\\$\\{(.*?)\\}".toRegex()) { match ->
        val key = match.groupValues[1]
        val value = values[key]
        if (value?.isNotEmpty() == true) value else ""
    }
}
