package de.visualdigits.msfs2024.main.commands

import de.visualdigits.msfs2024.service.SetupService.setupProject
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.required
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@OptIn(ExperimentalCli::class)
class SetupProjectCommand : Subcommand("setupproject", "Sets up the parameters for a given project name.") {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    val projectName by option(
        type = ArgType.String,
        shortName = "n",
        fullName = "projectname",
        description = "Project name. The given configuration will be stored with this name for later reuse (see docs)."
    ).required()

    val packageDir by option(
        type = ArgType.String,
        shortName = "p",
        fullName = "packagedir",
        description = "Absolute path containing the liveries layout.json"
    ).required()

    val textureSubPath by option(
        type = ArgType.String,
        shortName = "s",
        fullName = "texturesubpath",
        description = "Optional path to the texture folder relative to the package folder. When omitted the tool tries to find the texture folder itself"
    )

    val modelTexturesDir by option(
        type = ArgType.String,
        shortName = "m",
        fullName = "modelTexturesDir",
        description = "Absolute path which contains the textures in png format (i.e. the directory used by the blender model)."
    ).required()

    val textureTypes by option(
        type = ArgType.String,
        shortName = "t",
        fullName = "texturetypes",
        description = "Optional coma separated list of texture types [ALBD, COMP, DECAL, NORM] default is ALBD,COMP"
    )

    override fun execute() {
        setupProject(
            projectName,
            packageDir,
            textureSubPath,
            modelTexturesDir,
            textureTypes
        )

        log.info("Set up project '$projectName' parameters successfully")
    }
}
