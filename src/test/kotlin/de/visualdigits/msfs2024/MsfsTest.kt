package de.visualdigits.msfs2024

import java.io.File
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Only for local testing")
class MsfsTest {

    @Test
    fun testPngToKtx2() {
//        PngToKtx2Converter.convert(
//            texturesDir = File("E:/Games/MSFS 2024/Blender/Airplanes/General Aviation/Boom XB1/Boom XB1_Nasa/texture"),
//            packageDir = File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Boom XB1/Boom XB1 Nasa"),
//            imageToMSFSKTX2Path = File("E:/Games/MSFS 2024/ImageToMSFSKTX2")
//        )
    }

    @Test
    fun testKtx2ToPng() {
//        Ktx2ToPngConverter.convert(
//            sourceDir = File("E:\\Games\\MSFS 2024\\vfs\\SimObjects\\Airplanes\\boom-xb1\\common\\texture.ext"),
//            textureTypes = listOf("albd", "comp"),
//            texturesDir = File("E:\\Rendered\\Models\\Vehicles\\Airborne\\Boom XB1\\Paintkit\\texture"),
//            nvidiaTextureToolPath = File("C:/Program Files/NVIDIA Corporation/NVIDIA Texture Tools/nvtt_export.exe")
//        )
    }

    @Test
    fun testGenerateJson() {
        val packageDirectories = listOf(
            // Boom XB-1
            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Boom XB1/Boom XB1 Nasa"),

            // Condor-Retro-Pack
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Retro-Pack/FS24_DA62X_Condor-Retro-60s"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Retro-Pack/FS24_DA62X_Condor-Retro-70s"),

            // Condor-Stripes-Pack
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Stripes-Pack/FS24_DA62X_Condor-Stripes-Beach"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Stripes-Pack/FS24_DA62X_Condor-Stripes-Island"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Stripes-Pack/FS24_DA62X_Condor-Stripes-Sea"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Stripes-Pack/FS24_DA62X_Condor-Stripes-Sunshine"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Condor-Stripes-Pack/FS24_DA62X_Condor-Stripes-Passion"),

            // Flame-Pack
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Flame-Pack/FS24_DA62X_Blue-Flame"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_DA62X_Flame-Pack/FS24_DA62X_Green-Flame"),

            // Maharaja Airline
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Maharaja-Airlines-Pack/FS24_DA62X_Maharaja-Airlines-Leopard"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Maharaja-Airlines-Pack/FS24_DA62X_Maharaja-Airlines-Tiger"),

            // Beast Horus
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Beast Horus/beast-horus-mod"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Beast Horus/Beast-Horus_Sports-Pack/FS24_Beast-Horus_Sports-BlackYellow"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Beast Horus/Beast-Horus_Sports-Pack/FS24_Beast-Horus_Sports-BlueMetallic"),

            // Diamond DA42
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA42/cows-da42vi-SuperRed"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA42/cows-da42vi_Blue-Flame"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA42/cows-da42vi_Crazy-Diamond"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA42/cows-da42vi_Condor-Hans"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA42/cows-da42vi_Lufthansa-100"),

            // Daher TBM930
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Daher TBM930 1300hp/TBM930_Livery-Pack/FS24_Daher-TBM930_Blue-Metallic"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Daher TBM930 1300hp/TBM930_Livery-Pack/FS24_Daher-TBM930_Happy-Birthday"),

            // Paintkit Template
//            File("E:/Rendered/Models/Vehicles/Airborne/Diamond DA62/Paintkit/FS24_DA62X_Project-Name"),

            // Blimps
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Blimp/blimp-advertising-pack/Blimp-Manikin-Records"),

//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Military/F-22A Northern Fury"),

//            File("E:/Downloads/Games/MSFS 2024/Airplanes/DA62X_1uXFJ/FS24_DA62X"),
//            File("E:/Downloads/Games/MSFS 2024/Airplanes/FS24_DA62X_Blue-Flame"),
//            File("E:/Downloads/Games/MSFS 2024/Airplanes/FS24_DA62X_Blue-Flame"),
//            File("E:/Downloads/Games/MSFS 2024/Airplanes/FS24_DA62X_Color-Flame"),
//            File("C:/Users/sknul/AppData/Local/Packages/Microsoft.Limitless_8wekyb3d8bbwe/LocalCache/Packages/Community/FS24_DA62X_Condor-Retro"),
//            File("E:/Downloads/Games/MSFS 2024/Airplanes/a320-v2-condor-daicf"),
//            File("E:/Downloads/Games/MSFS 2024/Airplanes/vrilleaplat-cessna-t-37_Starfleet-Academy"),

            // Premium-Color-Pack
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Anthracite"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Champagner_Gold"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Glossy_White"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Light_Sunset_Orange"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Midnight_Green"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_OEFSG"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_OHFLT"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_PHILM"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Ruby_Red"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_SanMarinoBlue"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_SanMarinoBlue_Anthracite"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Sapphire_Blue"),
//            File("E:/Games/MSFS 2024/MainLibrary/Aircraft/Airplanes/Civilian/Diamond DA62X/FS24_Premium-Color-pack/FS24_Sapphire_Blue_Anthracite"),
        )

//        PngToKtx2Converter.generateLayoutJsonFiles("C:/Anwendungen/MSFSLayoutGenerator.exe", packageDirectories)
    }
}
