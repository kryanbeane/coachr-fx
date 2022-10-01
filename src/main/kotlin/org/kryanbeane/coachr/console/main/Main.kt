package org.kryanbeane.coachr.console.main

import org.kryanbeane.coachr.console.controllers.ClientController

val clientController = ClientController()

fun main() {
    clientController.start()
}