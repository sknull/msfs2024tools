package de.visualdigits.msfs2024tools.service

import de.visualdigits.msfs2024tools.service.SetupService.setupGlobal
import java.io.File
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SetupGlobalTest {

    @Test
    fun testSetupGlobalWithImageTool() {
        val currentDir = File(ClassLoader.getSystemResource(".").toURI()).canonicalPath
        val configFile = File(currentDir, "msfs2024Tools.json")
        if (configFile.exists()) configFile.delete()

        setupGlobal(
            imageToMSFSKTX2Path = File(ClassLoader.getSystemResource("./imageTool").toURI()).canonicalPath,
            simType = "STEAM",
            sdkRoot = "sdkRoot",
            layoutGeneratorToolPath = "layoutGeneratorToolPath",
            nvidiaTextureToolPath = "nvidiaTextureToolPath",
            currentDir = currentDir
        )
        
        val expected = "{\r\n" +
                "  \"simType\" : \"STEAM\",\r\n" +
                "  \"sdkRoot\" : \"sdkRootImageTool\",\r\n" +
                "  \"layoutGeneratorToolPath\" : \"layoutGeneratorToolPath\",\r\n" +
                "  \"flagHQAlbd\" : false,\r\n" +
                "  \"flagNoAlphaAlbd\" : true,\r\n" +
                "  \"flagHQDecal\" : false,\r\n" +
                "  \"flagNoAlphaDecal\" : true,\r\n" +
                "  \"nvidiaTextureToolPath\" : \"nvidiaTextureToolPath\",\r\n" +
                "  \"projects\" : [ ]\r\n" +
                "}"

        assertTrue(configFile.exists())
        assertEquals(expected, configFile.readText())
    }

    @Test
    fun testSetupGlobalWithImageToolWithoutConfig() {
        val currentDir = File(ClassLoader.getSystemResource(".").toURI()).canonicalPath
        val configFile = File(currentDir, "msfs2024Tools.json")
        if (configFile.exists()) configFile.delete()

        setupGlobal(
            imageToMSFSKTX2Path = File(ClassLoader.getSystemResource("./imageToolWithoutConfig").toURI()).canonicalPath,
            simType = "STEAM",
            sdkRoot = "sdkRoot",
            layoutGeneratorToolPath = "layoutGeneratorToolPath",
            nvidiaTextureToolPath = "nvidiaTextureToolPath",
            currentDir = currentDir
        )

        val expected = "{\r\n" +
                "  \"simType\" : \"STEAM\",\r\n" +
                "  \"sdkRoot\" : \"sdkRoot\",\r\n" +
                "  \"layoutGeneratorToolPath\" : \"layoutGeneratorToolPath\",\r\n" +
                "  \"flagHQAlbd\" : true,\r\n" +
                "  \"flagNoAlphaAlbd\" : false,\r\n" +
                "  \"flagHQDecal\" : true,\r\n" +
                "  \"flagNoAlphaDecal\" : false,\r\n" +
                "  \"nvidiaTextureToolPath\" : \"nvidiaTextureToolPath\",\r\n" +
                "  \"projects\" : [ ]\r\n" +
                "}"

        assertTrue(configFile.exists())
        assertEquals(expected, configFile.readText())
    }

    @Test
    fun testSetupGlobalWithoutImageTool() {
        val currentDir = File(ClassLoader.getSystemResource(".").toURI()).canonicalPath
        val configFile = File(currentDir, "msfs2024Tools.json")
        if (configFile.exists()) configFile.delete()

        setupGlobal(
            imageToMSFSKTX2Path = "imageToMSFSKTX2Path",
            simType = "STEAM",
            sdkRoot = "sdkRoot",
            layoutGeneratorToolPath = "layoutGeneratorToolPath",
            nvidiaTextureToolPath = "nvidiaTextureToolPath",
            currentDir = currentDir
        )

        val expected = "{\r\n" +
                "  \"simType\" : \"STEAM\",\r\n" +
                "  \"sdkRoot\" : \"sdkRoot\",\r\n" +
                "  \"layoutGeneratorToolPath\" : \"layoutGeneratorToolPath\",\r\n" +
                "  \"flagHQAlbd\" : true,\r\n" +
                "  \"flagNoAlphaAlbd\" : false,\r\n" +
                "  \"flagHQDecal\" : true,\r\n" +
                "  \"flagNoAlphaDecal\" : false,\r\n" +
                "  \"nvidiaTextureToolPath\" : \"nvidiaTextureToolPath\",\r\n" +
                "  \"projects\" : [ ]\r\n" +
                "}"

        assertTrue(configFile.exists())
        assertEquals(expected, configFile.readText())
    }
}
