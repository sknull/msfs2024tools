package de.visualdigits.msfs2024.model.types

import com.fasterxml.jackson.annotation.JsonValue

enum class TextureQuality(
    @JsonValue val value: String
) {

    HIGH("QUALITYHIGH"),
    LOW("")
}
