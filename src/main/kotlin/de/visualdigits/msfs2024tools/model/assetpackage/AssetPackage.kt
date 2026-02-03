package de.visualdigits.msfs2024tools.model.assetpackage


import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter
import de.visualdigits.msfs2024tools.model.jackson.EmptyToNullDeserializer
import java.io.File

class AssetPackage(
    @JacksonXmlProperty(isAttribute = true, localName = "Version") val version: String? = null,
    @JacksonXmlProperty(localName = "ItemSettings") val itemSettings: ItemSettings? = null,
    @JacksonXmlProperty(localName = "Flags") val flags: AssetPackageFlags? = AssetPackageFlags(),
    @JacksonXmlProperty(localName = "AssetGroups") val assetGroups: AssetGroups? = null
) {

    companion object {
        private val mapper = XmlMapper
            .builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION)
            .addModule(SimpleModule().addDeserializer(String::class.java, EmptyToNullDeserializer()))
            .build()

        val ASSET_PACKAGE_DEFAULT = AssetPackage(
            version = "0.1.0",
            itemSettings = ItemSettings(
                contentType = "AIRCRAFT",
                title = "PNG TO KTX2 CONVERTER",
                manufacturer = "VisualDigits",
                creator = "VisualDigits",
            ),
            flags = AssetPackageFlags(
                visibleInStore = true,
                canBeReferenced = true
            ),
            assetGroups = AssetGroups(
                listOf(
                    AssetGroup(
                        name = "PNG TO KTX2 CONVERTER",
                        type = "ModularSimObject",
                        flags = AssetGroupFlags(
                            fsXCompatibility = false
                        ),
                        assetDir = "PackageSources\\SimObjects\\Airplanes\\png-2-ktx2\\",
                        outputDir = "SimObjects\\Airplanes\\png-2-ktx2\\"
                    )
                )
            )
        )

        fun readValue(file: File): AssetPackage {
            return try {
                mapper.readValue(file, AssetPackage::class.java)
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


