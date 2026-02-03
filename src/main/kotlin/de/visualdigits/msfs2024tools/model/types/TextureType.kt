package de.visualdigits.msfs2024tools.model.types

enum class TextureType(
    val bitmapSlot: String,
    val forceNoAlpha: Boolean,
) {

    ALBD(
        bitmapSlot = "MTL_BITMAP_DECAL0",
        forceNoAlpha = false
    ),

    COMP(
        bitmapSlot = "MTL_BITMAP_METAL_ROUGH_AO",
        forceNoAlpha = true
    ),

    DECAL(
        bitmapSlot = "MTL_BITMAP_DECAL0",
        forceNoAlpha = false
    ),

    NORM(
        bitmapSlot = "MTL_BITMAP_NORMAL",
        forceNoAlpha = false
    )
}
