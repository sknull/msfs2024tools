package de.visualdigits.msfs2024.main.commands

import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.model.config.ProjectConfiguration
import de.visualdigits.msfs2024.model.types.TextureType
import de.visualdigits.util.expandVariables
import java.io.File
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class SetupProjectCommand : Subcommand("setupproject", "Sets up the parameters for a given project name.") {

    companion object {

        private const val NO_PACKAGE_DIR_GIVEN = "No package dir given"

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
    }

    val projectName by option(
        type = ArgType.String,
        shortName = "n",
        fullName = "projectname",
        description = "Project name. The given configuration will be stored with this name for later reuse (see docs)."
    )

    val packageDir by option(
        type = ArgType.String,
        shortName = "p",
        fullName = "packagedir",
        description = "Absolute path containing the liveries layout.json"
    )

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
    )

    val textureTypes by option(
        type = ArgType.String,
        shortName = "t",
        fullName = "texturetypes",
        description = "Optional coma separated list of texture types [ALBD, COMP, DECAL, NORM] default is ALBD,COMP"
    )

    override fun execute() {
        val currentDir = System.getProperty("user.dir")
        val configFile = File(currentDir, "msfs2024Tools.json")
        val config = Msfs2024Config.readValue(configFile)

        val packageTextureDir = textureSubPath?.let { tp -> File(packageDir?:error(NO_PACKAGE_DIR_GIVEN)).resolve(tp) }
            ?: File(packageDir?:error(NO_PACKAGE_DIR_GIVEN)).walkTopDown().firstOrNull { it.isDirectory && it.name == "texture" }
            ?: error("No texture path found")
        check(packageTextureDir.exists()) { "Model texture directory '$modelTexturesDir' does not exist" }

        config.projects.add(
            ProjectConfiguration(
                name = projectName?:error("No project name given"),
                packageDir = packageDir?:error(NO_PACKAGE_DIR_GIVEN),
                packageTextureDir = packageTextureDir.canonicalPath,
                modelTexturesDir = modelTexturesDir?:error("No model texture dir given"),
                textureTypes = textureTypes?.split(",")?.map { tt -> TextureType.valueOf(tt.trim()) }
                    ?: TextureType.entries
            )
        )

        config.writeValue(configFile)

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
