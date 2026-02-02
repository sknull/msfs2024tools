package de.visualdigits.msfs2024.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class AssetGroupFlags(
    @field:JacksonXmlProperty(localName = "FSXCompatibility") val fsXCompatibility: Boolean? = null
)
