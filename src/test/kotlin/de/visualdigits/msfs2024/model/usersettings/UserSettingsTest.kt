package de.visualdigits.msfs2024.model.usersettings

import java.io.File
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserSettingsTest {

    @Test
    fun testReadModel() {
        val file = File(ClassLoader.getSystemResource("msfs2024/png-2-ktx2.xml.user").toURI())
        val userSettings = UserSettings.readValue(file)

        val expected = file.readText()
        val actual = userSettings.writeValueAsString()
        assertEquals(expected, actual)
    }

    @Test
    fun testWriteModel() {
        println(UserSettings.USER_SETTINGS_DEFAULT.writeValueAsString())
    }
}
