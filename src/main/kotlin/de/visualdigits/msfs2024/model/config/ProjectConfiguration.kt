package de.visualdigits.msfs2024.model.config

import de.visualdigits.msfs2024.model.types.TextureType

/**
 * Project specific configuration.
 */
class ProjectConfiguration(

    /** Project name to store settings for. */
    val name: String = "",

    /** Absolute path to the directory containing the layout.json file for your target project. */
    val packageDir: String = "",

    /** Absolute path which contains the textures in ktx2 format. */
    val packageTextureDir: String = "",

    /** Absolute path which contains the textures in png format (i.e. the directory used by the blender model).  */
    val modelTexturesDir: String = "",

    /** Optional coma separated list of texture types to process [ALBD,COMP,DECAL,NORM], default is all. */
    val textureTypes: List<TextureType> = listOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectConfiguration

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
