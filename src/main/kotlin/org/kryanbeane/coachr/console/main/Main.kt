package org.kryanbeane.coachr.console.main

import org.kryanbeane.coachr.console.views.TornadoUI
import tornadofx.*

fun main(args: Array<String>) {
    launch<MainUIApp>(args)
}

class MainUIApp: App(TornadoUI::class)