package de.visualdigits.msfs2024tools.model.usersettings


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter
import de.visualdigits.msfs2024tools.model.common.Package
import de.visualdigits.msfs2024tools.model.common.Packages
import de.visualdigits.msfs2024tools.model.jackson.EmptyToNullDeserializer
import java.io.File


class UserSettings(
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JacksonXmlProperty(localName = "CheckedOutPackages")
    val checkedOutPackages: Packages? = null,

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JacksonXmlProperty(localName = "SelectedPackages")
    val selectedPackages: Packages? = null,

    @JacksonXmlProperty(localName = "Filter")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    val filter: String? = null,

    @JacksonXmlProperty(localName = "ShowOnlyEdited")
    val showOnlyEdited: Boolean? = false
) {

    companion object {
        private val mapper = XmlMapper
            .builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION)
            .defaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, JsonInclude.Include.NON_EMPTY))
            .addModule(SimpleModule().addDeserializer(String::class.java, EmptyToNullDeserializer()))
            .build()

        val USER_SETTINGS_DEFAULT = UserSettings(
            checkedOutPackages = Packages(listOf(Package( "png-2-ktx2"))),
            selectedPackages = Packages(listOf(Package( "png-2-ktx2"))),
            showOnlyEdited = false
        )

        fun readValue(file: File): UserSettings {
            return try {
                mapper.readValue(file, UserSettings::class.java)
            } catch (e: Exception) {
                throw IllegalStateException("Could not parse file '$file'", e)
            }
        }
    }

    fun writeValueAsString(): String {
        return mapper
            .setDefaultPrettyPrinter(DefaultXmlPrettyPrinter().withCustomNewLine("\r\n")) // ensure correct line separators for windoze
            .writeValueAsString(this)
    }

    fun writeValue(file: File) {
        mapper
            .setDefaultPrettyPrinter(DefaultXmlPrettyPrinter().withCustomNewLine("\r\n")) // ensure correct line separators for windoze
            .writeValue(file, this)
    }
}
