package de.visualdigits.msfs2024.main.commands

import de.visualdigits.msfs2024.model.types.SimType
import de.visualdigits.msfs2024.service.SetupService.setupGlobal
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
        setupGlobal(
            imageToMSFSKTX2Path,
            simType,
            sdkRoot,
            layoutGeneratorToolPath,
            nvidiaTextureToolPath
        )

        log.info("Set up global parameters successfully")
    }
}
