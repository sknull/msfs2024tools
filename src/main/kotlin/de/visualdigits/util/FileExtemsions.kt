package de.visualdigits.util

import java.io.File
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val log: Logger = LoggerFactory.getLogger(File::class.java)

fun File.copyToIfNotExists(targetFile: File) {
    if (!targetFile.exists()) {
        log.info("Copy file '${this.canonicalPath}' to '$targetFile'...")
        this.copyTo(targetFile)
    }
}

fun File.createDirectoryIfNotExists(): File {
    if (!exists()) {
        log.info("Creating target directory '${this.canonicalPath}'")
        if (!mkdirs()) {
            log.error("Could not create target directory")
        }
    }

    return this
}
