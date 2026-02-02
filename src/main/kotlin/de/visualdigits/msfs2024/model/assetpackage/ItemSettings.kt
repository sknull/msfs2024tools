package de.visualdigits.msfs2024.model.assetpackage


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class ItemSettings(
    @JacksonXmlProperty(localName = "ContentType") val contentType: String? = null,
    @JacksonXmlProperty(localName = "Title") val title: String? = null,
    @JacksonXmlProperty(localName = "Manufacturer") val manufacturer: String? = null,
    @JacksonXmlProperty(localName = "Creator") val creator: String? = null
)
