package de.visualdigits.msfs2024tools.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class AssetGroups(
    @JacksonXmlProperty(localName = "AssetGroup")
    @JacksonXmlElementWrapper(useWrapping = false)
    val assetGroup: List<AssetGroup> = listOf()
)
