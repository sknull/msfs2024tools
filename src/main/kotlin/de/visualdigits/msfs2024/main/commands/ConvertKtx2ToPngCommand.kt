package de.visualdigits.msfs2024.main.commands

import de.visualdigits.msfs2024.Ktx2ToPngConverter
import de.visualdigits.msfs2024.model.types.TextureType
import java.io.File
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class ConvertKtx2ToPngCommand : Subcommand("ktx2", "Convert ktx2 textures to png format for editing") {

    val projectName by option(
        type = ArgType.String,
        shortName = "n",
        fullName = "projectname",
        description = "Optional project name. The given configuration will be stored with this name for later reuse (see docs)."
    )

    override fun execute() {
        Ktx2ToPngConverter.convert(projectName?:error("No project name given."))
    }
}
