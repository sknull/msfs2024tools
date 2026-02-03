package de.visualdigits.msfs2024tools.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class AssetGroupFlags(
    @field:JacksonXmlProperty(localName = "FSXCompatibility") val fsXCompatibility: Boolean? = null
)
