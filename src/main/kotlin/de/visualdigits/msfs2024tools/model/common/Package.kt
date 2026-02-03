package de.visualdigits.msfs2024tools.model.common


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

class Package(
    @JacksonXmlProperty(isAttribute = true, localName = "Name") val name: String? = null
) {

    @JacksonXmlText
    var value: String? = null
        set(value) {
            field = value?.trim()
        }

    constructor(name: String? = null, value: String?): this(name) {
        this.value = value
    }
}
