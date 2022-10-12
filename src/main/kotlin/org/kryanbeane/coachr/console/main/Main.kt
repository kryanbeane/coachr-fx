package org.kryanbeane.coachr.console.main

import org.kryanbeane.coachr.console.controllers.ClientController

val clientController = ClientController(
    false,
    "coachr-clients-db",
    "coach-clients",
)

fun main() {
    clientController.start()
}