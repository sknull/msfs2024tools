package de.visualdigits.msfs2024.service

import de.visualdigits.msfs2024.model.config.Msfs2024Config
import de.visualdigits.msfs2024.service.SetupService.setupProject
import java.io.File
import kotlin.test.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SetupProjectTest {

    @Test
    fun testSetupProject() {
        val currentDir = File(ClassLoader.getSystemResource(".").toURI()).canonicalPath
        val configFile = File(currentDir, "msfs2024Tools.json")
        if (configFile.exists()) configFile.delete()

        setupProject(
            projectName = "projectName",
            packageDir = File(ClassLoader.getSystemResource("./packageDir").toURI()).canonicalPath,
            textureSubPath = null,
            modelTexturesDir = File(ClassLoader.getSystemResource("./modelTexturesDir").toURI()).canonicalPath,
            textureTypes = "ALBD, COMP",
            currentDir = currentDir
        )

        assertTrue(configFile.exists())
        val config = Msfs2024Config.readValue(configFile)
        val projectConfiguration = config["projectName"]
        assertNotNull(projectConfiguration)
        assertEquals("\\packageDir", projectConfiguration.packageDir.drop(currentDir.length))
        assertEquals("\\modelTexturesDir", projectConfiguration.modelTexturesDir.drop(currentDir.length))
        assertEquals("\\packageDir\\SimObjects\\Airplanes\\projectName\\liveries\\YourName\\projectName\\texture", projectConfiguration.packageTextureDir.drop(currentDir.length))
    }
}
