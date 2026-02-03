package de.visualdigits.msfs2024tools.model.descriptor

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import de.visualdigits.msfs2024tools.model.types.TextureQuality

class UserFlags(
    @field:JacksonXmlProperty(isAttribute = true, localName = "Type") val type: String = "_DEFAULT",
    @JacksonXmlText val textureQuality: TextureQuality? = TextureQuality.HIGH
)
