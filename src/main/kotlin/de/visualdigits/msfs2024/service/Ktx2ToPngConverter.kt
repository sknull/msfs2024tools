package de.visualdigits.msfs2024.service

import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.model.config.ProjectConfiguration
import de.visualdigits.util.runCommand
import java.io.File
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Ktx2ToPngConverter {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * Converts all ktx2 textures in the given sourceDir to png.
     */
    fun convert(
        projectName: String,
    ) {
        val currentDir = System.getProperty("user.dir")
        val msfs2024Config = Msfs2024Config.readValue(File(currentDir, "msfs2024Tools.json"))
        val projectConfiguration = msfs2024Config[projectName]?:error("No project configuration found for project '$projectName'")
        log.info("Converting ktx2 texture files in '${projectConfiguration.packageTextureDir}'")
        log.info("Using target texture directory: ${projectConfiguration.modelTexturesDir}")

        val processed = convertKtx2ToPng(projectConfiguration, msfs2024Config)
        if (processed) {
            log.info("Conversion finished.")
        } else {
            log.info("No images converted")
        }
    }

    private fun convertKtx2ToPng(
        projectConfiguration: ProjectConfiguration,
        msfs2024Config: Msfs2024Config
    ): Boolean {
        val suffixes = projectConfiguration.textureTypes.map { tt -> "$tt.png.ktx2" }
        val files = File(projectConfiguration.packageTextureDir)
            .listFiles { f -> f.isFile && suffixes.any { s -> f.name.endsWith(s, ignoreCase = true) } }
        files
            ?.forEach { f ->
                log.info("Converting texture file '${f.name}'")
                val targetFile = File(projectConfiguration.modelTexturesDir, f.name.dropLast(5))
                val command =
                    "\"${msfs2024Config.nvidiaTextureToolPath}\" -o \"${targetFile.canonicalPath}\" \"${f.canonicalPath}\""
                log.info("Executing command '$command'")
                command.runCommand(File(msfs2024Config.nvidiaTextureToolPath).parentFile)
            }

        return files.isNotEmpty()
    }
}
