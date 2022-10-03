package org.kryanbeane.coachr.console.main

import org.kryanbeane.coachr.console.controllers.ClientController
import org.kryanbeane.coachr.console.views.MainClientUI
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

class MainUIApp : App(MainClientUI::class)