package de.visualdigits.msfs2024.model.common


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class Packages(
    @JacksonXmlProperty(localName = "Package")
    @JacksonXmlElementWrapper(useWrapping = false)
    val `package`: List<Package> = listOf()
)
