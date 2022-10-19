package org.kryanbeane.coachr.console.main

import org.kryanbeane.coachr.console.views.TornadoUI
import tornadofx.*

//val clientController = ClientController(
//    false,
//    "coachr-clients-db",
//    "coach-clients",
//)

fun main(args: Array<String>) {
    launch<MainUIApp>(args)
    // clientController.start()
}

class MainUIApp: App(TornadoUI::class)