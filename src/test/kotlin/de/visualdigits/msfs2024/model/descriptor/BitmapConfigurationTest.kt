package de.visualdigits.msfs2024.model.descriptor

import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.model.types.TextureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BitmapConfigurationTest {

    private val msfs2024Config = Msfs2024Config()

    init {
        msfs2024Config.layoutGeneratorToolPath = "C:/"
    }

    @Test
    fun testDefaultAlbd() {
        val bitmapConfig = BitmapConfiguration(
            textureType = TextureType.ALBD,
            msfs2024Config = msfs2024Config
        )

        val expected = "<BitmapConfiguration><BitmapSlot>MTL_BITMAP_DECAL0</BitmapSlot><UserFlags Type=\"_DEFAULT\">QUALITYHIGH</UserFlags><ForceNoAlpha>false</ForceNoAlpha></BitmapConfiguration>"
        val actual = bitmapConfig.writeValueAsString()

        assertEquals(expected, actual)
    }

    @Test
    fun testDefaultComp() {
        val bitmapConfig = BitmapConfiguration(
            textureType = TextureType.COMP,
            msfs2024Config = msfs2024Config
        )

        val expected = "<BitmapConfiguration><BitmapSlot>MTL_BITMAP_METAL_ROUGH_AO</BitmapSlot><UserFlags Type=\"_DEFAULT\">QUALITYHIGH</UserFlags><ForceNoAlpha>true</ForceNoAlpha></BitmapConfiguration>"
        val actual = bitmapConfig.writeValueAsString()

        assertEquals(expected, actual)
    }

    @Test
    fun testDefaultDecal() {
        val bitmapConfig = BitmapConfiguration(
            textureType = TextureType.DECAL,
            msfs2024Config = msfs2024Config
        )

        val expected = "<BitmapConfiguration><BitmapSlot>MTL_BITMAP_DECAL0</BitmapSlot><UserFlags Type=\"_DEFAULT\">QUALITYHIGH</UserFlags><ForceNoAlpha>false</ForceNoAlpha></BitmapConfiguration>"
        val actual = bitmapConfig.writeValueAsString()

        assertEquals(expected, actual)
    }

    @Test
    fun testDefaultNorm() {
        val bitmapConfig = BitmapConfiguration(
            textureType = TextureType.NORM,
            msfs2024Config = msfs2024Config
        )

        val expected = "<BitmapConfiguration><BitmapSlot>MTL_BITMAP_NORMAL</BitmapSlot><UserFlags Type=\"_DEFAULT\">QUALITYHIGH</UserFlags><ForceNoAlpha>false</ForceNoAlpha></BitmapConfiguration>"
        val actual = bitmapConfig.writeValueAsString()

        assertEquals(expected, actual)
    }
}
