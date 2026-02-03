package de.visualdigits.msfs2024tools

import java.io.File
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Only for local testing")
@OptIn(ExperimentalPathApi::class)
class LayoutTest {

    @Test
    fun prepareFlavors() {
        File("E:/Downloads/Games/MSFS 2024/Airplanes/FS24_DA62X_Condor-Retro/SimObjects/Airplanes")
            .listFiles { file -> file.isDirectory }
            ?.forEach { directory ->
                println("Processing directory $directory...")
                val flavor = directory.name.replace("DA62X_", "")
                val flavorName = flavor.replace("-", "")
                val flavorSpaceName = flavor.replace("-", " ")

                val aircraftCfgFile = File(directory, "aircraft.cfg")
                aircraftCfgFile.writeText(aircraftCfgFile.readText().replace("FlavorName", flavorName))
                aircraftCfgFile.writeText(aircraftCfgFile.readText().replace("Flavor Name", flavorSpaceName))

                val textureDirectory = File(directory, "texture.FlavorName")
                val textureCfgFile = File(textureDirectory, "texture.cfg")
                textureCfgFile.writeText(textureCfgFile.readText().replace("FlavorName", flavorName))
                textureCfgFile.writeText(textureCfgFile.readText().replace("Flavor Name", flavorSpaceName))
                textureDirectory.renameTo(File(directory, "texture.$flavorName"))

                val panelDirectory = File(directory, "panel.FlavorName")
                panelDirectory.renameTo(File(directory, "panel.$flavorName"))
            }
    }

    private val aircraftrCfg = """
        [VERSION]
        major = 1
        minor = 0
        
        [VARIATION]
        base_container = "..\DA62X"
        
        [FLTSIM.0]
        title = "Diamond DA62 LiveryName" ; Variation name
        model = "" ; model folder
        panel = "PanelName" ; panel folder
        sound = "" ; sound folder
        texture = "TextureName" ; texture folder
        kb_checklists = "" ; Procedures/Checklist sibling file name
        kb_reference = "" ; Reference information sibling file name
        description = "TT:AIRCRAFT.DESCRIPTION" ; Variation description.
        wip_indicator = 0 ; know if the variation is good to go or still WIP : -1=Disabled, 0=Rough, 1=1st Pass, 2=Finished
        ui_manufacturer = "TT:AIRCRAFT.UI_MANUFACTURER" ; e.g. Boeing, Cessna
        ui_type = "DA62X" ; e.g. 747-400, 172
        ui_variation = "LiveryName" ; e.g. World Air, IFR Panel
        ui_typerole = "Twin Engine Prop" ; e.g. Single Engine Prop, Twin Engine Prop, Rotorcraft, etc
        ui_createdby = "MrTommymxr" ; e.g. Asobo Studio, Microsoft, Wings Creates, etc
        ui_thumbnailfile = "" ; app relative path to ThumbNail image file
        ui_certified_ceiling = 20000 ; service ceiling / max certified operating altitude (ft)
        ui_max_range = 1283 ; max distance the aircraft can fly between take-off and landing in (NM)
        ui_autonomy = 7 ; max duration the aircraft can fly between take-off and landing in (Hrs)
        ui_fuel_burn_rate = 79 ; average fuel consumption per hour (lbs/hr) - Legion: fuel density is ~6.7lbs per US gallon
        atc_id = "" ; tail number
        atc_id_enable = 0 ; enable tail number
        atc_airline = "" ; airline name
        icao_airline = "" ; airline icao code
        atc_flight_number = "DSK68" ; flight number
        atc_heavy = 0 ; heavy?
        atc_parking_types = "RAMP" ; "ANY" / "RAMP" / "CARGO" / "MIL_CARGO" / "MIL_COMBAT" / "GATE" / "DOCK"
        atc_parking_codes = "#FFFFFF" ; Comma separated and may be as small as one character each
        atc_id_color = "#ffff00ff" ; color for the tail number : i.e. "#ffff00ff"
        atc_id_font = "" ; font for the tail number
    """.trimIndent()

    private val textureCfg = """
        [fltsim]

        fallback.1=..\..\DA62X\texture
        fallback.2=..\..\..\texture\TextureFolder
        fallback.3=..\..\..\..\texture\detailMap
        fallback.4=..\..\..\..\texture\AS1000
        fallback.5=..\..\..\..\texture\Glass
        fallback.6=..\..\..\..\texture\Lights
        fallback.7=..\..\..\..\texture\Planes_Generic
    """.trimIndent()



    @Test
    fun convertMSFS2020() {
        File("E:/Games/MSFS 2024/Aircraft/Airplanes/Civilian/Diamond DA62X/Official Liveries")
            .listFiles { file -> file.isDirectory }
            ?.forEach { directory ->
                val liveryName = directory.name.replace("FS24_", "")
                println("Processing livery $liveryName...")

                val liveryDirectory = Paths.get(directory.canonicalPath, "Simobjects", "Airplanes", "DA62X_$liveryName").toFile()

                val aircraftCfgFile = File(liveryDirectory, "aircraft.cfg")
                val aircraftConfig = aircraftCfgFile
                    .readLines()
                    .filter { l -> l.trim().isNotBlank() && !l.startsWith("[")}
                    .map { l -> l.split("=").map { p -> p.trim() }.let { p -> Pair(p[0].trim(), p[1].split(";")[0].replace("\"", "").trim()) } }
                    .toMap()
                val panelName = aircraftConfig["panel"]?:""
                val textureName = aircraftConfig["texture"]?:""
                aircraftCfgFile.writeText(aircraftrCfg
                    .replace("LiveryName", liveryName)
                    .replace("PanelName", panelName)
                    .replace("TextureName", textureName)
                )

                val textureDirectory = File(liveryDirectory, "texture.$textureName")
                val textureCfgFile = File(textureDirectory, "texture.cfg")
                textureCfgFile.writeText(textureCfg.replace("TextureFolder", textureName))
            }
    }
}
