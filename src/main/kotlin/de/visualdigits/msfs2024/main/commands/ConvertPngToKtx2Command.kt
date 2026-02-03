package de.visualdigits.msfs2024.main.commands

import de.visualdigits.msfs2024.service.PngToKtx2Converter
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class ConvertPngToKtx2Command : Subcommand("png", "Convert png textures to ktx2 format for delivery") {

    val projectName by option(
        type = ArgType.String,
        shortName = "n",
        fullName = "projectname",
        description = "Optional project name. The given configuration will be stored with this name for later reuse (see docs)."
    )

    override fun execute() {
        PngToKtx2Converter.convert(projectName?:error("No project name given."))
    }
}
