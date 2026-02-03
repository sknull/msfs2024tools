package de.visualdigits.msfs2024.model.descriptor

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.model.types.TextureType
import java.io.File

@JacksonXmlRootElement()
class BitmapConfiguration(
    @JacksonXmlProperty(localName = "BitmapSlot") val bitmapSlot: String? = null,
    @JacksonXmlProperty(localName = "UserFlags") val userFlags: UserFlags? = null,
    @JacksonXmlProperty(localName = "ForceNoAlpha") val forceNoAlpha: Boolean? = null
) {

    constructor(
        textureType: TextureType,
        msfs2024Config: Msfs2024Config
    ): this(
        bitmapSlot = textureType.bitmapSlot,
        userFlags = UserFlags(textureQuality = msfs2024Config.textureQuality(textureType)),
        forceNoAlpha = msfs2024Config.forceNoAlpha(textureType)
    )

    private val mapper = XmlMapper
        .builder()
        .defaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, JsonInclude.Include.NON_EMPTY))
        .build()

    fun writeValueAsString(): String {
        return mapper.writeValueAsString(this)
    }

    fun writeValue(file: File) {
        mapper.writeValue(file, this)
    }
}
