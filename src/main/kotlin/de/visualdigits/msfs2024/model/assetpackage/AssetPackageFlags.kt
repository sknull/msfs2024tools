package de.visualdigits.msfs2024.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class AssetPackageFlags(
    @JacksonXmlProperty(localName = "VisibleInStore") val visibleInStore: Boolean? = null,
    @JacksonXmlProperty(localName = "CanBeReferenced") val canBeReferenced: Boolean? = null
)
