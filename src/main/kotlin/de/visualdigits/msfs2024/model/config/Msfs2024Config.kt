package de.visualdigits.msfs2024.model.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import de.visualdigits.msfs2024.model.types.SimType
import de.visualdigits.msfs2024.model.types.TextureQuality
import de.visualdigits.msfs2024.model.types.TextureType
import java.io.File

/**
 * Global configuration valid for all projects.
 */
class Msfs2024Config(

    /** Imported from imagetool - Simtype [MICROSFT, STEAM], default is MICROSOFT. */
    var simType: SimType = SimType.MICROSOFT,

    /** Imported from imagetool - Absolute path to the sdk (needed to convert png images to ktx2) default is 'C:/MSFS 2024 SDK'. */
    var sdkRoot: String = "C:/MSFS 2024 SDK",

    /** Imported from imagetool - Absolute path which contains layoutgenerator tool (needed to update layout.json) default is unset. */
    var layoutGeneratorToolPath: String = "",

    /** Imported from imagetool - Determines whether to output high quality for albedo textures, default is true. */
    var flagHQAlbd: Boolean = true,

    /** Imported from imagetool - Determines whether to force no alpha for albedo textures, default is false. */
    var flagNoAlphaAlbd: Boolean = false,

    /** Imported from imagetool - Determines whether to output high quality for decal textures, default is true. */
    var flagHQDecal: Boolean = true,

    /** Imported from imagetool - Determines whether to force no alpha for decal textures, default is false. */
    var flagNoAlphaDecal: Boolean = false,

    /** Absolute path to the nvidia texture exporter (needed to convert ktx2 to png), default is 'C:/Program Files/NVIDIA Corporation/NVIDIA Texture Tools/nvtt_export.exe' */
    var nvidiaTextureToolPath: String = "C:/Program Files/NVIDIA Corporation/NVIDIA Texture Tools/nvtt_export.exe",

    /**  */
    var projects: MutableSet<ProjectConfiguration> = mutableSetOf()
) {
    companion object {

        private val mapper = jacksonMapperBuilder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build()

        fun readValue(file: File): Msfs2024Config {
            return if (file.exists()) {
                mapper.readValue(file, Msfs2024Config::class.java)
            } else {
                Msfs2024Config()
            }
        }
    }

    private val projectConfigurations: Map<String, ProjectConfiguration>
        get() {
            return projects.associateBy { pc -> pc.name?:error("No project name given") }
        }

    fun writeValue(file: File) {
        mapper.writeValue(file, this)
    }

    operator fun get(projectName: String): ProjectConfiguration? {
        return projectConfigurations[projectName]
    }

    fun textureQuality(textureType: TextureType): TextureQuality {
        return when (textureType) {
            TextureType.ALBD -> if(flagHQAlbd) TextureQuality.HIGH else TextureQuality.LOW
            TextureType.DECAL -> if(flagHQDecal) TextureQuality.HIGH else TextureQuality.LOW
            else -> TextureQuality.HIGH
        }
    }

    fun forceNoAlpha(textureType: TextureType): Boolean {
        return when (textureType) {
            TextureType.ALBD -> flagNoAlphaAlbd
            TextureType.DECAL -> flagNoAlphaDecal
            else -> textureType.forceNoAlpha
        }
    }
}
