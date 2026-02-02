package de.visualdigits.msfs2024

import de.visualdigits.msfs2024.model.assetpackage.AssetPackage
import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.model.config.ProjectConfiguration
import de.visualdigits.msfs2024.model.descriptor.BitmapConfiguration
import de.visualdigits.msfs2024.model.project.Project
import de.visualdigits.msfs2024.model.types.SimType
import de.visualdigits.msfs2024.model.types.TextureType
import de.visualdigits.msfs2024.model.usersettings.UserSettings
import de.visualdigits.util.WindowsUtils
import de.visualdigits.util.copyToIfNotExists
import de.visualdigits.util.createDirectoryIfNotExists
import de.visualdigits.util.runCommand
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object PngToKtx2Converter {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * Refreshes all textures by converting all png textures in the given sourceDir
     * to ktx2 textures and put them into the given texturePath relative to the given
     * packageDir. Finally, the layout.json file in the packageDir is regenerated.
     */
    fun convert(
        projectName: String,
    ) {
        val currentDir = System.getProperty("user.dir")
        val msfs2024Config = Msfs2024Config.readValue(File(currentDir, "msfs2024Tools.json"))
        val projectConfiguration = msfs2024Config[projectName]?:error("No project configuration found for project '$projectName'")
        
        val processed = convertPngToKtx2(
            msfs2024Config,
            projectConfiguration
        )
        if (processed) {
            generateLayoutJsonFile(
                msfs2024Config,
                projectConfiguration
            )
        } else {
            log.info("No images converted - not regenerating layout.json")
        }

        log.info("Conversion finished.")
    }

    /**
     * Converts all png textures in the given sourceDir to ktx2.
     */
    fun convertPngToKtx2(
        msfs2024Config: Msfs2024Config,
        projectConfiguration: ProjectConfiguration
    ): Boolean {
        log.info("Converting png texture files in '${projectConfiguration.modelTexturesDir}'")
        log.info("Using target texture directory: ${projectConfiguration.packageTextureDir}")

        val tempDir = File(projectConfiguration.modelTexturesDir, "TempPackage").createDirectoryIfNotExists()
        prepareTempPackage(tempDir)
        val processed = preprocessTextures(
            msfs2024Config,
            projectConfiguration
        )
        if (processed) {
            runPackageTool(
                msfs2024Config,
                tempDir
            )
            collectConvertedTextures(projectConfiguration)
        } else {
            log.info("Found no modified textures - not running package tool")
        }
        tempDir.deleteRecursively()

        return processed
    }

    fun generateLayoutJsonFile(
        msfs2024Config: Msfs2024Config,
        projectConfiguration: ProjectConfiguration
    ) {
        log.info("Generating ${projectConfiguration.packageDir}/layout.json")

        val layoutFile = File(projectConfiguration.packageDir, "layout.json")

        "\"${msfs2024Config.layoutGeneratorToolPath}\" \"${layoutFile.canonicalPath}\""
            .runCommand(File(msfs2024Config.layoutGeneratorToolPath).parentFile)
    }

    private fun prepareTempPackage(tempDir: File) {
        val packageDefinitionsDir = File(tempDir, "PackageDefinitions").createDirectoryIfNotExists()
        AssetPackage.ASSET_PACKAGE_DEFAULT.writeValue(File(packageDefinitionsDir, "png-2-ktx2.xml"))
        Project.PROJECT_DEFAULT.writeValue(File(tempDir, "png-2-ktx2.xml"))
        UserSettings.USER_SETTINGS_DEFAULT.writeValue(File(tempDir, "png-2-ktx2.xml.user"))
    }

    /**
     * Copies all png texture files in the given sourceDir along with the
     * appropriate descriptor file for the texture type to the package directory.
     *
     * Returns true if textures have been modified and need to be converted, false otherwise.
     */
    private fun preprocessTextures(
        msfs2024Config: Msfs2024Config,
        projectConfiguration: ProjectConfiguration
    ): Boolean {
        log.info("Preprocessing source directory '${projectConfiguration.modelTexturesDir}'...")

        val tempTargetDir = File(projectConfiguration.modelTexturesDir)
                .resolve("TempPackage/PackageSources/SimObjects/Airplanes/png-2-ktx2/common/texture")
                .createDirectoryIfNotExists()
        val modifiedFiles = determineModifiedFiles(projectConfiguration)
        modifiedFiles
            .forEach { f ->
                val textureType = determineTextureType(f)
                val targetFile = File(tempTargetDir, f.name)
                if (!targetFile.exists()) {
                    log.info("  Copy texture file '${f.name}'")
                    f.copyToIfNotExists(targetFile)
                }
                log.info("  Generating descriptor file '${f.name}.xml'")
                val bitmapConfiguration = BitmapConfiguration(textureType = textureType, msfs2024Config = msfs2024Config)
                bitmapConfiguration.writeValue(File(tempTargetDir, "${f.name}.xml"))
            }

        return modifiedFiles.isNotEmpty()
    }

    /**
     * Determine texture files in the source folder which have been modified compared to the target folder.
     */
    private fun determineModifiedFiles(
        projectConfiguration: ProjectConfiguration
    ): List<File> {
        val targetFiles = File(projectConfiguration.packageTextureDir)
            .listFiles { f -> f.name.endsWith(".ktx2", ignoreCase = true) }
            ?.associateBy { f -> f.name.lowercase().dropLast(5) }
            ?: mapOf()
        return File(projectConfiguration.modelTexturesDir)
            .listFiles { f -> f.name.endsWith(".png", ignoreCase = true) }
            ?.associateBy { f -> f.name.lowercase() }
            ?.map { (key, value) -> Pair(value, targetFiles[key]) }
            ?.filter { p -> p.second != null && p.first.lastModified() > p.second!!.lastModified() }
            ?.mapNotNull { p -> p.first }
            ?: listOf()
    }

    private fun runPackageTool(
        msfs2024Config: Msfs2024Config,
        tempDir: File
    ) {
        val msfsPackageToolPath = Paths.get(msfs2024Config.sdkRoot,"Tools", "bin", "fspackagetool.exe").absolutePathString()
        check(File(msfsPackageToolPath).exists()) { "Package tool '$msfsPackageToolPath' does not exist - terminating" }

        val paramSteam = if (msfs2024Config.simType == SimType.STEAM) " -forcesteam" else ""
        val command = "\"$msfsPackageToolPath\" -nopause -rebuild$paramSteam -outputtoseparateconsole \"png-2-ktx2.xml\""
        log.info("Executing command '$command'...")

        command.runCommand(tempDir)
        var tasks = WindowsUtils.getRunningTasks()
        while (tasks.any("Abbildname", "FlightSimulator2024.exe")) {
            Thread.sleep(100)
            tasks = WindowsUtils.getRunningTasks()
        }
        log.info("Command finished.")
    }

    private fun collectConvertedTextures(
        projectConfiguration: ProjectConfiguration
    ) {
        log.info("Copy ktx2 textures with descriptors...")
        val finalTexturesDir = File(projectConfiguration.modelTexturesDir)
            .resolve("TempPackage/Packages/png-2-ktx2/SimObjects/Airplanes/png-2-ktx2/common/texture")
        findFiles(finalTexturesDir, listOf(".KTX2", ".json"))
            .forEach { f -> f.copyTo(File(projectConfiguration.packageTextureDir, f.name), overwrite = true) }
    }

    fun findFiles(dir: File, suffixes: List<String>): List<File> {
        return if (dir.exists()) {
            dir.listFiles { f -> f.isFile && suffixes.any { s -> f.name.endsWith(s, ignoreCase = true) } }
                ?.toList()
                ?:listOf()
        } else {
            listOf()
        }
    }

    private fun determineTextureType(textureFile: File): TextureType {
        return when {
            textureFile.name.endsWith("_albd.png", ignoreCase = true) -> {
                TextureType.ALBD
            }
            textureFile.name.endsWith("_comp.png", ignoreCase = true) -> {
                TextureType.COMP
            }
            textureFile.name.endsWith("_decal.png", ignoreCase = true) -> {
                TextureType.DECAL
            }
            textureFile.name.endsWith("_norm.png", ignoreCase = true) -> {
                TextureType.NORM
            }
            else -> {
                error("Unsupoorted texture file: $textureFile")
            }
        }
    }
}
