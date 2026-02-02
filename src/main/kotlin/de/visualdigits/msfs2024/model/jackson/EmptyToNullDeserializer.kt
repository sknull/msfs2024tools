package de.visualdigits.msfs2024.model.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class EmptyToNullDeserializer : JsonDeserializer<String>() {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): String? {
        val value = p.text
        return if (value.isNullOrBlank()) null else value
    }
}
