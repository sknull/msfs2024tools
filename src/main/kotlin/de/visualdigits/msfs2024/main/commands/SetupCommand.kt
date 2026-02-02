package de.visualdigits.msfs2024.main.commands

import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.model.types.SimType
import java.io.File
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@OptIn(ExperimentalCli::class)
class SetupCommand : Subcommand("setup", "Creates or updates the tool configuration file.") {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    val imageToMSFSKTX2Path by option(
        type = ArgType.String,
        shortName = "i",
        fullName = "imagetomsfsktx2path",
        description = "Absolute path to the folder which contains imageToMSFSKTX2. Settings will be imported."
    )

    val simType by option(
        type = ArgType.String,
        shortName = "s",
        fullName = "simType",
        description = "Determines whether you use the Microsoft or Steam version of the sim [MICROSOFT, STEAM], default is MICROSOFT"
    ).default(SimType.MICROSOFT.name)

    val sdkRoot by option(
        type = ArgType.String,
        shortName = "k",
        fullName = "sdkRoot",
        description = "Absolute path to the msfs sdk, default is C:\\MSFS 2024 SDK"
    ).default("C:\\MSFS 2024 SDK")

    val layoutGeneratorToolPath by option(
        type = ArgType.String,
        shortName = "l",
        fullName = "layoutGeneratorToolPath",
        description = "Absolute path to the layout generator tool. When omitted this path is taken from the imageToMSFSKTX2 userSettings.ini"
    )

    val nvidiaTextureToolPath by option(
        type = ArgType.String,
        shortName = "n",
        fullName = "nvidiatexturetoolpath",
        description = "Absolute path to the nvidia texture tool."
    ).default("C:\\Program Files\\NVIDIA Corporation\\NVIDIA Texture Tools\\nvtt_export.exe")

    override fun execute() {
        val currentDir = System.getProperty("user.dir")
        val configFile = File(currentDir, "msfs2024Tools.json")
        val config = Msfs2024Config.readValue(configFile)
        config.nvidiaTextureToolPath = nvidiaTextureToolPath

        if (imageToMSFSKTX2Path != null && File(imageToMSFSKTX2Path!!).exists()) {
            val userConfigFile = File(imageToMSFSKTX2Path, "userConfig.ini")
            if (userConfigFile.exists()) {
                val valueMap = userConfigFile.readLines()
                    .associate { l ->
                        l.split("=").let { ll ->
                            Pair(ll[0].trim(), ll[1].trim())
                        }
                    }.toMutableMap()

                config.simType = (valueMap["SimType"]?.let { st -> SimType.valueOf(st) }) ?: SimType.valueOf(simType)
                config.sdkRoot = valueMap["sdk_root"] ?: sdkRoot
                config.layoutGeneratorToolPath = layoutGeneratorToolPath
                    ?: (valueMap["LG_path"]?.let { f -> File(f, "MSFSLayoutGenerator.exe").canonicalPath })
                    ?: error("No layout generator path given")
                config.flagHQAlbd = valueMap["HQ_Flag_Albd"]?.let { v -> v == "ON" } ?: true
                config.flagNoAlphaAlbd = valueMap["NoAlpha_Flag_Albd"]?.let { v -> v == "ON" } ?: false
                config.flagHQDecal = valueMap["HQ_Flag_Decal"]?.let { v -> v == "ON" } ?: true
                config.flagNoAlphaDecal = valueMap["NoAlpha_Flag_Decal"]?.let { v -> v == "ON" } ?: false
            }
        } else {
            log.info("No imageToMSFSKTX2Path given - not importing configuration - using defaults")
        }

        config.writeValue(configFile)
    }
}
