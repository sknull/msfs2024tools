package de.visualdigits.msfs2024tools.service

import de.visualdigits.msfs2024tools.model.config.Msfs2024Config
import de.visualdigits.msfs2024tools.model.types.SimType
import de.visualdigits.util.expandVariables
import java.io.File
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object SetupService {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private const val BATCH_PNG_TO_KTX2_TEMPLATE = "rem Convert png to ktx2 for '\${projectName}'\r\n" +
            "\r\n\r\n" +
            "\${deviceLetter}:\r\n" +
            "cd \"\${path}\"\r\n" +
            "msfs2024Tools.exe png -n \"\${projectName}\"\r\n" +
            "pause\r\n"

    private const val BATCH_KTX2_TO_PNG_TEMPLATE = "rem Convert ktx2 to png for '\${projectName}'\r\n" +
            "\r\n\r\n" +
            "\${deviceLetter}:\r\n" +
            "cd \"\${path}\"\r\n" +
            "msfs2024Tools.exe ktx2 -n \"\${projectName}\"\r\n" +
            "pause\r\n"


    fun setupGlobal(
        imageToMSFSKTX2Path: String?,
        simType: String,
        sdkRoot: String,
        layoutGeneratorToolPath: String?,
        nvidiaTextureToolPath: String,
        currentDir: String = System.getProperty("user.dir") // for testing
    ) {
        val configFile = File(currentDir, "msfs2024Tools.json")
        val config = Msfs2024Config.readValue(configFile)
        config.nvidiaTextureToolPath = nvidiaTextureToolPath

        if (imageToMSFSKTX2Path != null && File(imageToMSFSKTX2Path).exists()) {
            val userConfigFile = File(imageToMSFSKTX2Path, "userConfig.ini")
            if (userConfigFile.exists()) {
                val valueMap = readIniValues(userConfigFile)
                config.simType = (valueMap["SimType"]?.let { st -> SimType.valueOf(st) }) ?: SimType.valueOf(simType)
                config.sdkRoot = valueMap["sdk_root"] ?: sdkRoot
                config.layoutGeneratorToolPath = layoutGeneratorToolPath
                    ?: (valueMap["LG_path"]?.let { f -> File(f, "MSFSLayoutGenerator.exe").canonicalPath })
                            ?: error("No layout generator path given")
                config.flagHQAlbd = valueMap["HQ_Flag_Albd"]?.let { v -> v == "ON" } ?: true
                config.flagNoAlphaAlbd = valueMap["NoAlpha_Flag_Albd"]?.let { v -> v == "ON" } ?: false
                config.flagHQDecal = valueMap["HQ_Flag_Decal"]?.let { v -> v == "ON" } ?: true
                config.flagNoAlphaDecal = valueMap["NoAlpha_Flag_Decal"]?.let { v -> v == "ON" } ?: false
            } else {
                setUpGlobalDefaults(config, simType, sdkRoot, nvidiaTextureToolPath, layoutGeneratorToolPath)
            }
        } else {
            setUpGlobalDefaults(config, simType, sdkRoot, nvidiaTextureToolPath, layoutGeneratorToolPath)
        }

        config.writeValue(configFile)
    }

    fun setupProject(
        projectName: String,
        packageDir: String,
        textureSubPath: String?,
        modelTexturesDir: String,
        textureTypes: String?,
        currentDir: String = System.getProperty("user.dir") // for testing
    ) {
        val configFile = File(currentDir, "msfs2024Tools.json")
        val config = Msfs2024Config.readValue(configFile)
        val packageTextureDir = determineTextureSubDir(packageDir, textureSubPath, modelTexturesDir)

        config.addProject(projectName, packageDir, packageTextureDir, modelTexturesDir, textureTypes)
        config.writeValue(configFile)
        generateBatchFiles(currentDir, projectName)
    }

    private fun setUpGlobalDefaults(
        config: Msfs2024Config,
        simType: String,
        sdkRoot: String,
        nvidiaTextureToolPath: String,
        layoutGeneratorToolPath: String?
    ) {
        log.info("No imageToMSFSKTX2Path given - not importing configuration - using given values or defaults")
        config.simType = SimType.valueOf(simType)
        config.sdkRoot = sdkRoot
        config.nvidiaTextureToolPath = nvidiaTextureToolPath
        config.layoutGeneratorToolPath = layoutGeneratorToolPath ?: error("No layoutGeneratorToolPath given")
        config.flagHQAlbd = true
        config.flagNoAlphaAlbd = false
        config.flagHQDecal = true
        config.flagNoAlphaDecal = false
    }

    private fun readIniValues(userConfigFile: File): Map<String, String> {
        val valueMap = userConfigFile.readLines()
            .associate { l ->
                l.split("=").let { ll ->
                    Pair(ll[0].trim(), ll[1].trim())
                }
            }
        return valueMap
    }

    private fun determineTextureSubDir(
        packageDir: String?,
        textureSubPath: String?,
        modelTexturesDir: String?
    ): File {
        val packageTextureDir =
            textureSubPath?.let { tp -> File(packageDir ?: error("No package dir given")).resolve(tp) }
                ?: File(packageDir ?: error("No package dir given")).walkTopDown()
                    .firstOrNull { it.isDirectory && it.name == "texture" }
                ?: error("No texture path found")
        check(packageTextureDir.exists()) { "Model texture directory '$modelTexturesDir' does not exist" }
        return packageTextureDir
    }

    private fun generateBatchFiles(
        currentDir: String,
        projectName: String
    ) {
        val values = mapOf(
            "deviceLetter" to currentDir.substringBefore(":"),
            "path" to currentDir,
            "projectName" to projectName
        )

        val batchKtx2File = File(currentDir, "${projectName}_ktx2ToPng.bat")
        batchKtx2File.writeText(BATCH_KTX2_TO_PNG_TEMPLATE.expandVariables(values))

        val batchPngFile = File(currentDir, "${projectName}_PngToKtx2.bat")
        batchPngFile.writeText(BATCH_PNG_TO_KTX2_TEMPLATE.expandVariables(values))
    }
}
