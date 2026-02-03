package de.visualdigits.msfs2024tools.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class AssetGroup(
    @JacksonXmlProperty(isAttribute = true, localName = "Name") val name: String? = null,
    @JacksonXmlProperty(localName = "Type") val type: String? = null,
    @JacksonXmlProperty(localName = "Flags") val flags: AssetGroupFlags? = null,
    @JacksonXmlProperty(localName = "AssetDir") val assetDir: String? = null,
    @JacksonXmlProperty(localName = "OutputDir") val outputDir: String? = null
)
