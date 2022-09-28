package org.kryanbeane.coachr.console.controllers

import mu.KotlinLogging
import org.kryanbeane.coachr.console.models.ClientMemStore
import org.kryanbeane.coachr.console.models.ClientModel
import org.kryanbeane.coachr.console.models.WorkoutModel
import org.kryanbeane.coachr.console.views.ClientView

class ClientController {
    var clients= ClientMemStore()
    private var clientView = ClientView()
    private var logger = KotlinLogging.logger{}
    var currentClient: ClientModel? = null

    init {
        logger.info { "Launching Coachr Console Application" }
        println("Coachr App Version 1.0")
    }

    fun start() {
        var input: Int
        do {
            input = clientView.mainMenuView()
            when(input) {
                1 -> addNewClient()
                2 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun clientMenu() {
        var input: Int
        do {
            input = clientView.clientMenuView()
            when(input) {
                1 -> viewClients()
                2 -> editClients()
                3 -> deleteClient()
                4 -> start()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun viewClients() {
        var input: Int
        do {
            input = clientView.viewClientsMenuView()
            when(input) {
                1 -> viewAllClients()
                2 -> println()
                3 -> println()
                4 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun editClients() {
        var input: Int
        do {
            input = clientView.editClientMenuView()
            when(input) {
                1 -> println()
                2 -> workoutController.editWorkoutPlan()
                3 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun editWorkoutPlan() {
        var input: Int
        do {
            input = clientView.editWorkoutPlanMenuView()
            when(input) {
                1 -> addWorkoutToClient(currentClient!!)
                2 -> editWorkout()
                3 -> println()
                4 -> editClients()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun editWorkout() {
        var input: Int
        do {
            input = clientView.editWorkoutMenuView()
            when(input) {
                1 -> println()
                2 -> println()
                3 -> println()
                4 -> editWorkoutPlan()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun findOrListMenu() {
        var input: Int
        do {
            input = clientView.findOrListMenuView()
            when(input) {
                1 -> findClient()
                2 -> listClients()
                3 -> clientMenu()
                0 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != 0)
        logger.info { "Shutting Down Coachr" }
    }

    fun viewAllClients() {
        clientView.listClients(clients)
    }

    fun addNewClient() {
        val newClient = ClientModel()
        if(clientView.clientDataIsValid(newClient)) {
            clients.createClient(newClient)
            logger.info("Client Added : [ ${newClient.fullName} ]")
        }
        else
            logger.error("Invalid Client Details, please try again")
    }

    fun addWorkoutToClient(client: ClientModel) {
        if(client != null) {
            val newWorkout = WorkoutModel()
            if(clientView.workoutDataIsValid(newWorkout)) {
                clients.createClientWorkout(client, newWorkout)
                logger.info("Workout Added : [ ${newWorkout.name} ]")
            }
            else
                logger.error("Invalid Workout Details, please try again")
        }
        else
            logger.error("No Client Selected")
    }



}