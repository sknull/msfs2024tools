package de.visualdigits.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtensionsTest {

    private val template = "hello \${name} you \${bar}\nWhat is your \${foo} \${baz} \${bazfoo}"

    @Test
    fun testReplace() {
        val values = mapOf(
            "name" to "Stephan",
            "bar" to "Developer",
            "foo" to "name",
            "baz" to ""
        )
        assertEquals("hello Stephan you Developer\nWhat is your name  ", template.expandVariables(values))
    }
}
