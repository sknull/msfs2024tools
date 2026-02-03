package de.visualdigits.msfs2024tools.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class AssetPackageFlags(
    @JacksonXmlProperty(localName = "VisibleInStore") val visibleInStore: Boolean? = null,
    @JacksonXmlProperty(localName = "CanBeReferenced") val canBeReferenced: Boolean? = null
)
