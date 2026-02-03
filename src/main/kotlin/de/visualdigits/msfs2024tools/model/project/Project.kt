package de.visualdigits.msfs2024tools.model.project


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

class Project(
    @JacksonXmlProperty(isAttribute = true, localName = "Version") val version: Int? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "Name") val name: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "FolderName") val folderName: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "MetadataFolderName") val metadataFolderName: String? = null,
    @JacksonXmlProperty(localName = "OutputDirectory") val outputDirectory: String? = null,
    @JacksonXmlProperty(localName = "TemporaryOutputDirectory") val temporaryOutputDirectory: String? = null,
    @JacksonXmlProperty(localName = "Packages") val packages: Packages? = null
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

        val PROJECT_DEFAULT = Project(
            version = 2,
            name = "PNG TO KTX2 CONVERTER",
            folderName = "Packages",
            metadataFolderName = "PackagesMetadata",
            outputDirectory = ".",
            temporaryOutputDirectory = "_PackageInt",
            packages = Packages(listOf(Package(value = "PackageDefinitions\\png-2-ktx2.xml")))
        )

        fun readValue(file: File): Project {
            return try {
                mapper.readValue(file, Project::class.java)
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

