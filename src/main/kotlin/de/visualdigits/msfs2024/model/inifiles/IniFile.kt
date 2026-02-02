package de.visualdigits.msfs2024.model.inifiles

import java.io.File

abstract class IniFile(
    val file: File? = null
) {

    val valueMap: MutableMap<String, String?> = if(file?.exists() == true) {
        file.readLines()
            .associate { l ->
                l.split("=").let { ll ->
                    Pair(ll[0].trim(), ll[1].trim())
                }
            }.toMutableMap()
    } else {
        mutableMapOf()
    }

    override fun toString(): String {
        return valueMap
            .filter { e -> e.value != null }.entries
            .joinToString("\r\n") { e -> "${e.key}=${e.value}" } + "\r\n"
    }

    fun save() {
        file?.writeText(toString())
    }
}
