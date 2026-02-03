package de.visualdigits.msfs2024tools.model.project

import java.io.File
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProjectTest {

    @Test
    fun testReadModel() {
        val file = File(ClassLoader.getSystemResource("msfs2024/png-2-ktx2.xml").toURI())
        val project = Project.readValue(file)

        val expected = file.readText()
        val actual = project.writeValueAsString()
        assertEquals(expected, actual)
    }

    @Test
    fun testWriteModel() {
        println(Project.PROJECT_DEFAULT.writeValueAsString())
    }
}
