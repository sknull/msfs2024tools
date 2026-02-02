package de.visualdigits.msfs2024.main

import de.visualdigits.msfs2024.main.commands.ConvertKtx2ToPngCommand
import de.visualdigits.msfs2024.main.commands.ConvertPngToKtx2Command
import de.visualdigits.msfs2024.main.commands.SetupCommand
import de.visualdigits.msfs2024.main.commands.SetupProjectCommand
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
class Main{

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val parser = ArgParser("msfs2024tools")
            parser.subcommands(
                SetupCommand(),
                SetupProjectCommand(),
                ConvertKtx2ToPngCommand(),
                ConvertPngToKtx2Command()
            )
            parser.parse(args)
        }
    }
}
