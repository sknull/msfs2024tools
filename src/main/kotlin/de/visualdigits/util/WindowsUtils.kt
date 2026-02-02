package de.visualdigits.util

import java.io.File
import java.util.concurrent.TimeUnit

object WindowsUtils {

    fun getRunningTasks(): Table {
        val (stdout, stderr) = "tasklist".runCommand(File("C:\\Windows\\SysWOW64"), Pair(0, TimeUnit.SECONDS))
        check(stderr == null) { "Error while executing command: $stderr" }
        val lines = stdout?.trim()?.split("\r\n") ?: listOf()
        val template = lines[1].split(" ").map { t -> t.length }
        val table = Table(
            keys = splitByTemplate(lines[0], template),
            data = lines.drop(2).map { line -> splitByTemplate(line, template) }
        )
        return table
    }

    private fun splitByTemplate(
        line: String,
        template: List<Int>
    ): List<String> {
        var buffer = line
        return template.map { len ->
            val token = buffer.take(len).trim()
            buffer = buffer.drop(len + 1)
            token
        }
    }
}
